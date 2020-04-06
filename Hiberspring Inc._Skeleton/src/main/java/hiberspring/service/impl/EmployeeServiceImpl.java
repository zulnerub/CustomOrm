package hiberspring.service.impl;

import hiberspring.domain.dtos.EmployeeRootSeedDto;
import hiberspring.domain.dtos.EmployeeSeedDto;
import hiberspring.domain.dtos.EmployeeViewDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Employee;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.repository.EmployeeRepository;
import hiberspring.service.BranchService;
import hiberspring.service.EmployeeCardService;
import hiberspring.service.EmployeeService;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static hiberspring.common.GlobalConstants.EMPLOYEES_FILE_PATH;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final BranchService branchService;
    private final EmployeeCardService employeeCardService;
    private final ValidationUtil validationUtil;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, XmlParser xmlParser, ModelMapper modelMapper, BranchService branchService, EmployeeCardService employeeCardService, ValidationUtil validationUtil) {
        this.employeeRepository = employeeRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.branchService = branchService;
        this.employeeCardService = employeeCardService;
        this.validationUtil = validationUtil;
    }


    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return Files.readString(Path.of(EMPLOYEES_FILE_PATH));
    }

    @Override
    public String importEmployees() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        EmployeeRootSeedDto employeeRootSeedDto = this.xmlParser
                .parseXml(EmployeeRootSeedDto.class, EMPLOYEES_FILE_PATH);

        employeeRootSeedDto.getEmployees()
                .forEach(e -> {
                    if (this.validationUtil.isValid(e)) {
                        if (this.employeeRepository.findByCard_Number(e.getCard()).orElse(null) == null) {
                            Branch branch = this.branchService.getByName(e.getBranch());
                            EmployeeCard card = this.employeeCardService.getByNumber(e.getCard());
                            if (branch != null && card != null) {
                                Employee employee = this.modelMapper.map(e, Employee.class);
                                employee.setBranch(branch);
                                employee.setCard(card);

                                sb.append(String.format("Succesfully imported %s %s %s",
                                        employee.getClass().getSimpleName(), employee.getFirstName(), employee.getLastName()));

                                this.employeeRepository.saveAndFlush(employee);
                            } else {
                                sb.append("Error: Invalid data.");
                            }
                        } else {
                            sb.append("Error: Invalid data.");
                        }
                    } else {
                        sb.append("Error: Invalid data.");
                    }
                    sb.append(System.lineSeparator());
                });


        return sb.toString();
    }

    @Override
    public String exportProductiveEmployees() {
        StringBuilder sb = new StringBuilder();

        this.employeeRepository.findAllByBranch_ProductsSizeGreaterThanOne()
                .forEach(e -> {
                    EmployeeViewDto emp = this.modelMapper.map(e, EmployeeViewDto.class);
                    emp.setFullName(e.getFirstName() + " " + e.getLastName());
                    sb.append(
                    String.format("Name: %s\n" +
                            "Position: %s\n" +
                            "Card Number: %s\n" +
                            "-------------------------", emp.getFullName(), emp.getPosition(), emp.getCardNumber()));
                    sb.append(System.lineSeparator());
                });


        return sb.toString();
    }
}
