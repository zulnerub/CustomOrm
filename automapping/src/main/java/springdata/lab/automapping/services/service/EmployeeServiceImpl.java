package springdata.lab.automapping.services.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdata.lab.automapping.data.entities.Address;
import springdata.lab.automapping.data.entities.City;
import springdata.lab.automapping.data.entities.Employee;
import springdata.lab.automapping.data.repositories.EmployeeRepository;
import springdata.lab.automapping.services.dtos.AddressDto;
import springdata.lab.automapping.services.dtos.CityDto;
import springdata.lab.automapping.services.dtos.EmployeeSeedDto;
import springdata.lab.automapping.services.dtos.EmployeeViewDto;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;
    private final CityService cityService;
    private final AddressService addressService;

    @Autowired
    public EmployeeServiceImpl(ModelMapper modelMapper, EmployeeRepository employeeRepository, CityService cityService, AddressService addressService) {
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
        this.cityService = cityService;
        this.addressService = addressService;
    }

    @Override
    public void save(EmployeeSeedDto employeeSeedDto) {
        Employee employee = this.modelMapper.map(employeeSeedDto, Employee.class);

        CityDto cityDto = this.cityService.getByName(employeeSeedDto.getAddressCity());

        AddressDto addressDto = this.addressService.getByCity(this.modelMapper.map(
                cityDto, City.class
        ));

        employee.setAddress(this.modelMapper.map(addressDto, Address.class));

        this.employeeRepository.save(employee);
    }

    @Override
    public EmployeeViewDto findByFirstNameAndLastName(String fn, String ln) {
        return this.modelMapper.map(
                this.employeeRepository.findByFirstNameAndLastName(fn, ln),
                EmployeeViewDto.class
        );
    }
}
