package springdata.lab.automapping.services.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdata.lab.automapping.data.entities.City;
import springdata.lab.automapping.data.repositories.CityRepository;
import springdata.lab.automapping.services.dtos.CityDto;
import springdata.lab.automapping.services.dtos.CitySeedDto;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(CitySeedDto city) {
        this.cityRepository.save(this.modelMapper.map(city, City.class));
    }

    @Override
    public CityDto getByName(String name) {
        return this.modelMapper.map(this.cityRepository.findByName(name), CityDto.class);
    }
}
