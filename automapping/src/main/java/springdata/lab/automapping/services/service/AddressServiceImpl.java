package springdata.lab.automapping.services.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdata.lab.automapping.data.entities.Address;
import springdata.lab.automapping.data.entities.City;
import springdata.lab.automapping.data.repositories.AddressRepository;
import springdata.lab.automapping.services.dtos.AddressDto;
import springdata.lab.automapping.services.dtos.AddressSeedDto;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(AddressSeedDto address) {
        this.addressRepository.save(this.modelMapper.map(address, Address.class));
    }

    @Override
    public AddressDto getByCity(City city) {
        return this.modelMapper.map(this.addressRepository.findByCity(city), AddressDto.class);
    }
}
