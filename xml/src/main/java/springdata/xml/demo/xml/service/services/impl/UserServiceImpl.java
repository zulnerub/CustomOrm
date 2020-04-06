package springdata.xml.demo.xml.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springdata.xml.demo.xml.domain.entities.Phone;
import springdata.xml.demo.xml.domain.entities.User;
import springdata.xml.demo.xml.domain.repositories.UserRepository;
import springdata.xml.demo.xml.service.dtos.PhoneDto;
import springdata.xml.demo.xml.service.dtos.UserDto;
import springdata.xml.demo.xml.service.dtos.UserRootDto;
import springdata.xml.demo.xml.service.dtos.UserSeedDto;
import springdata.xml.demo.xml.service.services.PhoneService;
import springdata.xml.demo.xml.service.services.UserService;
import springdata.xml.demo.xml.utils.XmlParser;


import javax.xml.bind.JAXBException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final String USERS_FILE_PATH = "src/main/resources/xmls/users.xml";
    private final PhoneService phoneService;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, XmlParser xmlParser, PhoneService phoneService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.phoneService = phoneService;
    }


    @Override
    public void save(UserSeedDto userSeedDto) {
        this.userRepository.save(this.modelMapper.map(userSeedDto, User.class));
    }

    @Override
    public void seedUsers() throws JAXBException {
        UserRootDto dtos = this.xmlParser.importFromXml(UserRootDto.class, USERS_FILE_PATH);

        
        for (UserDto userDto : dtos.getUsers()) {
            User user = this.modelMapper.map(userDto, User.class);
            Set<Phone> phones = new HashSet<>();
            for (PhoneDto phoneDto : userDto.getPhoneRootDto().getPhoneDtoList()) {
                this.phoneService.save(phoneDto);
                phones.add(this.phoneService.getPhoneByPhoneNumber(phoneDto.getPhoneNumber()));
            }

            user.setPhones(phones);

            this.userRepository.save(user);
        }
    }

    @Override
    public List<UserDto> findAll() {
        return this.userRepository.findAll()
                .stream()
                .map(u -> this.modelMapper.map(u, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(long id) {
        return this.modelMapper
                .map(Objects.requireNonNull(this.userRepository.findById(id).orElse(null)), UserDto.class);
    }

    @Override
    public void exportUsers() throws JAXBException {
        List<UserDto> users = this.findAll();

        UserRootDto userRootDto = new UserRootDto();
        userRootDto.setUsers(users);
        this.xmlParser.exportToXml(userRootDto ,USERS_FILE_PATH);

    }
}
