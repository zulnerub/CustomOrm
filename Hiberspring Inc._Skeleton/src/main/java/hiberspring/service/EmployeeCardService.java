package hiberspring.service;

import hiberspring.domain.entities.EmployeeCard;

import java.io.FileNotFoundException;
import java.io.IOException;

//TODO
public interface EmployeeCardService {
    EmployeeCard getByNumber(String number);

    Boolean employeeCardsAreImported();

    String readEmployeeCardsJsonFile() throws IOException;

    String importEmployeeCards(String employeeCardsFileContent) throws FileNotFoundException;

}
