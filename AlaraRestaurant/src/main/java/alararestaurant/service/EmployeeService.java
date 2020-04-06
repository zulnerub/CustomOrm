package alararestaurant.service;

import alararestaurant.domain.entities.Employee;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface EmployeeService {
    Employee getByName(String name);

    Boolean employeesAreImported();

    String readEmployeesJsonFile() throws IOException;

    String importEmployees(String employees) throws IOException;
}
