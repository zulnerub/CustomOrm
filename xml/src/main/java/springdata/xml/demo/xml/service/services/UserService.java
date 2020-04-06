package springdata.xml.demo.xml.service.services;

import springdata.xml.demo.xml.service.dtos.UserDto;
import springdata.xml.demo.xml.service.dtos.UserSeedDto;

import javax.xml.bind.JAXBException;
import java.util.List;

public interface UserService {

    void save(UserSeedDto userSeedDto);

    void seedUsers() throws JAXBException;

    List<UserDto> findAll();

    UserDto findById(long id);

    void exportUsers() throws JAXBException;
}
