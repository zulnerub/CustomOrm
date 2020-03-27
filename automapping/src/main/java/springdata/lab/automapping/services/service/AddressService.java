package springdata.lab.automapping.services.service;

import org.springframework.stereotype.Service;
import springdata.lab.automapping.data.entities.City;
import springdata.lab.automapping.services.dtos.AddressDto;
import springdata.lab.automapping.services.dtos.AddressSeedDto;

@Service
public interface AddressService {

    void save(AddressSeedDto address);

    AddressDto getByCity(City city);
}
