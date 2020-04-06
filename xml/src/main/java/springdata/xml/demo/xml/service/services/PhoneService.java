package springdata.xml.demo.xml.service.services;

import springdata.xml.demo.xml.domain.entities.Phone;
import springdata.xml.demo.xml.service.dtos.PhoneDto;

public interface PhoneService {

    void save(PhoneDto phoneDto);

    Phone getPhoneByPhoneNumber(String phoneNumber);
}
