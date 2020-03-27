package springdata.lab.automapping.services.service;

import org.springframework.stereotype.Service;
import springdata.lab.automapping.services.dtos.CityDto;
import springdata.lab.automapping.services.dtos.CitySeedDto;

@Service
public interface CityService {

    void save(CitySeedDto city);

    CityDto getByName(String name);
}
