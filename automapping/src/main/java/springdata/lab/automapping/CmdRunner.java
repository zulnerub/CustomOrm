package springdata.lab.automapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import springdata.lab.automapping.services.dtos.*;
import springdata.lab.automapping.services.service.AddressService;
import springdata.lab.automapping.services.service.CityService;
import springdata.lab.automapping.services.service.EmployeeService;


@Component
public class CmdRunner implements CommandLineRunner {

    private final EmployeeService employeeService;
    private final AddressService addressService;
    private final CityService cityService;


    @Autowired
    public CmdRunner(EmployeeService employeeService, AddressService addressService, CityService cityService) {
        this.employeeService = employeeService;
        this.addressService = addressService;
        this.cityService = cityService;
    }


    @Override
    public void run(String... args) throws Exception {

        /*
        CitySeedDto city = new CitySeedDto("Sofia");
        CitySeedDto city1 = new CitySeedDto("Plovdiv");
        CitySeedDto city2 = new CitySeedDto("Varna");

        this.cityService.save(city);
        this.cityService.save(city1);
        this.cityService.save(city2);

        AddressSeedDto address = new AddressSeedDto(this.cityService.getByName("Sofia"));
        AddressSeedDto address1 = new AddressSeedDto(this.cityService.getByName("Plovdiv"));
        AddressSeedDto address2 = new AddressSeedDto(this.cityService.getByName("Varna"));

        this.addressService.save(address);
        this.addressService.save(address1);
        this.addressService.save(address2);

        EmployeeSeedDto employeeSeedDto =
                new EmployeeSeedDto("Ivan", "Ivanov", 13200, "Sofia");
        this.employeeService.save(employeeSeedDto);

        EmployeeSeedDto employeeSeedDto1 =
                new EmployeeSeedDto("Petar", "Petrov", 245, "Sofia");
        this.employeeService.save(employeeSeedDto1);

        EmployeeSeedDto employeeSeedDto2 =
                new EmployeeSeedDto("Sulio", "Sulev", 97897, "Sofia");
        this.employeeService.save(employeeSeedDto2);


        EmployeeViewDto employeeViewDto = this.employeeService.findByFirstNameAndLastName("Ivan", "Ivanov");
*/

        System.out.println();

    }
}
