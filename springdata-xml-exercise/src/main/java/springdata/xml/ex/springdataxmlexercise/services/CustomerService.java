package springdata.xml.ex.springdataxmlexercise.services;

import springdata.xml.ex.springdataxmlexercise.models.dtos.CustomerSeedDto;
import springdata.xml.ex.springdataxmlexercise.models.dtos.CustomerViewRootDto;
import springdata.xml.ex.springdataxmlexercise.models.entities.Customer;

import java.util.List;

public interface CustomerService {
    void seedCustomers(List<CustomerSeedDto> customers);

    Customer getRandomCustomer();

    CustomerViewRootDto getAllOrderedCustomers();

}
