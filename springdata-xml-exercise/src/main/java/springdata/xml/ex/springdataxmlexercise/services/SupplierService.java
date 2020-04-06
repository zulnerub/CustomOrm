package springdata.xml.ex.springdataxmlexercise.services;

import springdata.xml.ex.springdataxmlexercise.models.dtos.SupplierSeedDto;
import springdata.xml.ex.springdataxmlexercise.models.dtos.SupplierViewRootDto;
import springdata.xml.ex.springdataxmlexercise.models.entities.Supplier;

import java.util.List;

public interface SupplierService {
    void seedSuppliers(List<SupplierSeedDto> suppliers);

    Supplier getRandomSupplier();

    SupplierViewRootDto getAllNotImporting();
}
