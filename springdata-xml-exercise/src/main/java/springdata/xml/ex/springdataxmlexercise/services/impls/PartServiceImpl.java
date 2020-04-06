package springdata.xml.ex.springdataxmlexercise.services.impls;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springdata.xml.ex.springdataxmlexercise.models.dtos.PartSeedDto;
import springdata.xml.ex.springdataxmlexercise.models.entities.Part;
import springdata.xml.ex.springdataxmlexercise.repositories.PartRepository;
import springdata.xml.ex.springdataxmlexercise.services.PartService;
import springdata.xml.ex.springdataxmlexercise.services.SupplierService;
import springdata.xml.ex.springdataxmlexercise.utils.ValidatorUtil;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class PartServiceImpl implements PartService {
    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final SupplierService supplierService;
    private final Random random;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil, SupplierService supplierService, Random random) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.supplierService = supplierService;
        this.random = random;
    }


    @Override
    public void seedParts(List<PartSeedDto> parts) {
        parts.forEach(p -> {
                    if (this.validatorUtil.isValid(p)){
                        if (this.partRepository.findByName(p.getName()) == null){
                            Part part =  this.modelMapper.map(p, Part.class);
                            part.setSupplier(this.supplierService.getRandomSupplier());
                            this.partRepository.saveAndFlush(part);
                        }else {
                            System.out.println("Part already exists!");
                        }
                    }else{
                        this.validatorUtil.violations(p)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }

    @Override
    public Part getRandomPart() {
        long randomId =(this.random.nextInt((int) this.partRepository.count()) + 1);
        return this.partRepository.getOne(randomId);
    }
}
