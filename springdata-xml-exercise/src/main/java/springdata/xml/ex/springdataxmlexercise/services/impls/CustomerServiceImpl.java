package springdata.xml.ex.springdataxmlexercise.services.impls;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springdata.xml.ex.springdataxmlexercise.models.dtos.CustomerSeedDto;
import springdata.xml.ex.springdataxmlexercise.models.dtos.CustomerViewDto;
import springdata.xml.ex.springdataxmlexercise.models.dtos.CustomerViewRootDto;
import springdata.xml.ex.springdataxmlexercise.models.entities.Customer;
import springdata.xml.ex.springdataxmlexercise.repositories.CustomerRepository;
import springdata.xml.ex.springdataxmlexercise.services.CustomerService;
import springdata.xml.ex.springdataxmlexercise.utils.ValidatorUtil;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final Random random;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper, Random random) {
        this.customerRepository = customerRepository;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.random = random;
    }


    @Override
    public void seedCustomers(List<CustomerSeedDto> customers) {
        customers
                .forEach(c -> {
                    if (this.validatorUtil.isValid(c)) {
                        if (this.customerRepository.findByNameAndBirthDate(c.getName(), c.getBirthDate()) == null) {
                            this.customerRepository.saveAndFlush(
                                    this.modelMapper.map(
                                            c, Customer.class
                                    )
                            );


                        } else {
                            System.out.println("Customer already exists!");
                        }
                    } else {
                        this.validatorUtil.violations(c)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }

    @Override
    public Customer getRandomCustomer() {
        long randomId = this.random.nextInt((int) this.customerRepository.count()) + 1;
        return this.customerRepository.getOne(randomId);
    }

    @Override
    public CustomerViewRootDto getAllOrderedCustomers() {
        CustomerViewRootDto customerViewRootDto = new CustomerViewRootDto();

        List<CustomerViewDto> customerViewDtos =
                this.customerRepository.findAllByBirthDateAndIsYoungDriver()
                .stream()
                .map(c -> this.modelMapper.map(c, CustomerViewDto.class))
                .collect(Collectors.toList());

        System.out.println();
        customerViewRootDto.setCustomers(customerViewDtos);

        return customerViewRootDto;
    }
}
