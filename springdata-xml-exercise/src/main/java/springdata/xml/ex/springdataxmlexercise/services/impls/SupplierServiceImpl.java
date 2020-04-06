package springdata.xml.ex.springdataxmlexercise.services.impls;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdata.xml.ex.springdataxmlexercise.models.dtos.SupplierSeedDto;
import springdata.xml.ex.springdataxmlexercise.models.dtos.SupplierViewDto;
import springdata.xml.ex.springdataxmlexercise.models.dtos.SupplierViewRootDto;
import springdata.xml.ex.springdataxmlexercise.models.entities.Supplier;
import springdata.xml.ex.springdataxmlexercise.repositories.SupplierRepository;
import springdata.xml.ex.springdataxmlexercise.services.SupplierService;
import springdata.xml.ex.springdataxmlexercise.utils.ValidatorUtil;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final Random random;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil, Random random) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.random = random;
    }

    @Override
    public void seedSuppliers(List<SupplierSeedDto> suppliers) {
        suppliers
                .forEach(s -> {
                    if (this.validatorUtil.isValid(s)) {
                        if (this.supplierRepository.findByName(s.getName()) == null){
                            this.supplierRepository.saveAndFlush(
                                    this.modelMapper.map(s, Supplier.class)
                            );
                        }else {
                            System.out.println("Supplier already exists!");
                        }
                    } else {
                        this.validatorUtil
                                .violations(s)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }

    @Override
    public Supplier getRandomSupplier() {
        long randomId = this.random
                .nextInt((int) this.supplierRepository.count()) + 1;

        return this.supplierRepository.getOne(randomId);
    }

    @Override
    public SupplierViewRootDto getAllNotImporting() {
        SupplierViewRootDto supplierViewRootDto = new SupplierViewRootDto();

        List<SupplierViewDto> supplierViewDtos = this.supplierRepository.findAllByImporterFalse()
                .stream()
                .map(s -> {
                    SupplierViewDto supp = this.modelMapper.map(s, SupplierViewDto.class);

                    supp.setPartsCount(s.getParts().size());

                    return supp;
                })
                .collect(Collectors.toList());

        supplierViewRootDto.setSupplierViewDtoList(supplierViewDtos);

        return supplierViewRootDto;

    }
}
