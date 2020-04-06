package springdata.xml.ex.springdataxmlexercise.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springdata.xml.ex.springdataxmlexercise.models.entities.Sale;
import springdata.xml.ex.springdataxmlexercise.repositories.SaleRepository;
import springdata.xml.ex.springdataxmlexercise.services.CarService;
import springdata.xml.ex.springdataxmlexercise.services.CustomerService;
import springdata.xml.ex.springdataxmlexercise.services.SaleService;

import java.util.Random;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final CustomerService customerService;
    private final CarService carService;
    private final Random random;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, CustomerService customerService, CarService carService, Random random ) {
        this.saleRepository = saleRepository;
        this.customerService = customerService;
        this.carService = carService;
        this.random = random;
    }


    @Override
    public void seedSales() {
        for (int i = 0; i < 30; i++) {
            Sale sale = new Sale();
            sale.setCar(this.carService.getRandomCar());
            sale.setCustomer(this.customerService.getRandomCustomer());
            sale.setDiscount(this.getRandomDiscount());

            this.saleRepository.saveAndFlush(sale);
        }
    }
    
    private Double getRandomDiscount(){
        Double[] discounts = new Double[]{0D, 5D, 10D, 15D, 20D, 30D, 40D, 50D};
        
        return discounts[this.random.nextInt(discounts.length)];
    }
}
