package springdata.lab.automapping.services.service;

import springdata.lab.automapping.services.dtos.EmployeeSeedDto;
import springdata.lab.automapping.services.dtos.EmployeeViewDto;

public interface EmployeeService {
    void save(EmployeeSeedDto employeeSeedDto);

    EmployeeViewDto findByFirstNameAndLastName(String fn, String ln);
}
