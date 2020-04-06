package springdata.xml.demo.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import springdata.xml.demo.xml.service.services.PhoneService;
import springdata.xml.demo.xml.service.services.UserService;

@Component
public class CmdRunner implements CommandLineRunner {
    private final UserService userService;
    private final PhoneService phoneService;


    @Autowired
    public CmdRunner(UserService userService, PhoneService phoneService) {
        this.userService = userService;
        this.phoneService = phoneService;
    }


    @Override
    public void run(String... args) throws Exception {



        //this.userService.exportUsers();
       this.userService.seedUsers();
        /*

        List<UserDto> users = this.userService.findAll();

        UserRootDto userDto = new UserRootDto();
        userDto.setUsers(users);

        JAXBContext context = JAXBContext.newInstance(userDto.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(userDto, new File(USERS_FILE_PATH));
*/

/*
        UserSeedDto userSeedDto =
                new UserSeedDto("Simo", "Pepov", "Smolqn");

        UserSeedDto userSeedDto1 =
                new UserSeedDto("Ivan", "Kokanov", "Varna");

        UserSeedDto userSeedDto2 =
                new UserSeedDto("Chocho", "Ignatiev", "Sofiq");

        this.userService.save(userSeedDto);
        this.userService.save(userSeedDto1);
        this.userService.save(userSeedDto2);

 */




    }
}
