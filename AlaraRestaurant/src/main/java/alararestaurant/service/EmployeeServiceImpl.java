package alararestaurant.service;

import alararestaurant.domain.dtos.EmployeeSeedDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Position;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.PositionRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

import static alararestaurant.constants.GlobalConstants.EMPLOYEES_FILE_PATH;


@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final PositionRepository positionRepository;
    private final Gson gson;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validationUtil, PositionRepository positionRepository, Gson gson) {
        this.employeeRepository = employeeRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.positionRepository = positionRepository;
        this.gson = gson;
    }

    @Override
    public Employee getByName(String name) {
        return this.employeeRepository.findByName(name).orElse(null);
    }

    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesJsonFile() throws IOException {
        return this.fileUtil.readFile(EMPLOYEES_FILE_PATH);
    }

    @Override
    public String importEmployees(String employees) throws IOException {
        StringBuilder sb = new StringBuilder();

        EmployeeSeedDto[] esd = this.gson.fromJson(this.fileUtil.readFile(EMPLOYEES_FILE_PATH), EmployeeSeedDto[].class);

        Arrays.stream(esd).forEach(e -> {
            if (this.validationUtil.isValid(e)){
                Employee employee = this.modelMapper.map(e, Employee.class);
                if (this.positionRepository.findByName(e.getPosition()).orElse(null) == null){
                    this.positionRepository.saveAndFlush(new Position(e.getPosition()));
                }
                employee.setPosition(this.positionRepository.findByName(e.getPosition()).get());
                System.out.println();

                sb.append(String.format("Record %s successfully imported.", employee.getName()));
                this.employeeRepository.saveAndFlush(employee);
            }else {
                sb.append("Invalid data format.");
            }

            sb.append(System.lineSeparator());
        });

        return sb.toString();
    }
}
