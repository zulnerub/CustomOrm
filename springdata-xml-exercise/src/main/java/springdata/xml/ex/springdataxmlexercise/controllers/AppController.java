package springdata.xml.ex.springdataxmlexercise.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import springdata.xml.ex.springdataxmlexercise.models.dtos.*;
import springdata.xml.ex.springdataxmlexercise.services.*;
import springdata.xml.ex.springdataxmlexercise.utils.XmlParser;

import javax.xml.bind.JAXBException;

import java.io.BufferedReader;
import java.io.IOException;

import static springdata.xml.ex.springdataxmlexercise.constants.GlobalConstant.*;

@Component
public class AppController implements CommandLineRunner {

    private final XmlParser xmlParser;
    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;
    private final BufferedReader bufferedReader;

    @Autowired
    public AppController(XmlParser xmlParser, SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService, BufferedReader bufferedReader) {
        this.xmlParser = xmlParser;
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
        this.bufferedReader = bufferedReader;
    }


    @Override
    public void run(String... args) throws Exception {
        //this.seedSupplier();
        //this.seedParts();
        //this.seedCars();
        //this.seedCustomers();
        //this.seedSales();

       // this.writeAllCustomersOrdered();

        //this.writeAllCarsByMake();

       // this.writeAllSuppliersNotImporting();

        this.writeAllCarsWithListOfParts();

    }

    private void writeAllCarsWithListOfParts() throws JAXBException {
        CarViewRootNoIdDto carViewRootNoIdDto =
                this.carService.getAllCarsWithListOfParts();

        this.xmlParser.exportToXml(carViewRootNoIdDto, EXERCISE_FOUR);
    }

    private void writeAllSuppliersNotImporting() throws JAXBException {
        SupplierViewRootDto supplierViewRootDto =
                this.supplierService.getAllNotImporting();

        this.xmlParser.exportToXml(supplierViewRootDto, EXERCISE_THREE);
    }

    private void writeAllCarsByMake() throws IOException, JAXBException {
        System.out.println("Please enter Car Make: ");
        String make = bufferedReader.readLine();

        CarViewRootDto carViewRootDto =
                this.carService.getCarsByMakeOrdered(make);

        this.xmlParser.exportToXml(carViewRootDto, EXERCISE_TWO);

    }

    private void writeAllCustomersOrdered() throws JAXBException {
        CustomerViewRootDto customerViewRootDto =
                this.customerService.getAllOrderedCustomers();

        this.xmlParser.exportToXml(customerViewRootDto,EXERCISE_ONE);
    }

    private void seedSales() {
        this.saleService.seedSales();
    }

    private void seedCustomers() throws JAXBException {
        CustomerSeedRootDto customers =
                this.xmlParser.importFromXml(CustomerSeedRootDto.class, CUSTOMERS_FILE_PATH);

        this.customerService.seedCustomers(customers.getCustomers());
    }

    private void seedCars() throws JAXBException {
        CarSeedRootDto cars =
                this.xmlParser.importFromXml(CarSeedRootDto.class, CARS_FILE_PATH);

        this.carService.seedCars(cars.getCars());
    }

    private void seedParts() throws JAXBException {
        PartSeedRootDto parts =
                this.xmlParser.importFromXml(PartSeedRootDto.class, PARTS_FILE_PATH);

        this.partService.seedParts(parts.getParts());
    }

    private void seedSupplier() throws JAXBException {
        SupplierSeedRootDto suppliers =
                this.xmlParser.importFromXml(SupplierSeedRootDto.class, SUPPLIERS_FILE_PATH);

        this.supplierService.seedSuppliers(suppliers.getSuppliers());
        System.out.println();
    }
}
