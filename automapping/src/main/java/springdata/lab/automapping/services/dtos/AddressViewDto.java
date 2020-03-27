package springdata.lab.automapping.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@NoArgsConstructor
@Getter
@Service
@AllArgsConstructor
public class AddressViewDto {

    private CityDto city;
    private Set<EmployeeViewDto> employeeViewDtoSet;
}
