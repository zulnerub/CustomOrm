package springdata.lab.automapping.services.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class EmployeeViewDto {

    private String firstName;

    private double salary;

    private String addressCityName;
}
