package springdata.xml.demo.xml.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springdata.xml.demo.xml.domain.entities.Phone;
import springdata.xml.demo.xml.domain.repositories.PhoneRepository;
import springdata.xml.demo.xml.service.dtos.PhoneDto;
import springdata.xml.demo.xml.service.services.PhoneService;

@Service
public class PhoneServiceImpl implements PhoneService {

    private final PhoneRepository phoneRepository;
    private final ModelMapper modelMapper;

    public PhoneServiceImpl(PhoneRepository phoneRepository, ModelMapper modelMapper) {
        this.phoneRepository = phoneRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(PhoneDto phoneDto) {
        this.phoneRepository.save(this.modelMapper.map(phoneDto, Phone.class));
    }

    @Override
    public Phone getPhoneByPhoneNumber(String phoneNumber) {
        return this.phoneRepository.findByPhoneNumber(phoneNumber);
    }
}
