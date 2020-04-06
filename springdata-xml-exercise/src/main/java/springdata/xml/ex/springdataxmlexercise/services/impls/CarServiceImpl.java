package springdata.xml.ex.springdataxmlexercise.services.impls;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springdata.xml.ex.springdataxmlexercise.models.dtos.*;
import springdata.xml.ex.springdataxmlexercise.models.entities.Car;
import springdata.xml.ex.springdataxmlexercise.models.entities.Part;
import springdata.xml.ex.springdataxmlexercise.repositories.CarRepository;
import springdata.xml.ex.springdataxmlexercise.services.CarService;
import springdata.xml.ex.springdataxmlexercise.services.PartService;
import springdata.xml.ex.springdataxmlexercise.utils.ValidatorUtil;

import javax.validation.ConstraintViolation;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final Random random;
    private final PartService partService;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil, Random random, PartService partService) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.random = random;
        this.partService = partService;
    }

    @Override
    public void seedCars(List<CarSeedDto> cars) {
        cars
                .forEach(c -> {
                    if (this.validatorUtil.isValid(c)){
                        Car car =  this.modelMapper.map(c, Car.class);
                        Set<Part> parts = new HashSet<>();
                        int partSize = this.random.nextInt(10) + 10;
                        for (int i = 0; i < partSize; i++) {
                            parts.add(this.partService.getRandomPart());
                        }
                        car.setParts(parts);
                        this.carRepository.saveAndFlush(car);

                    }else{
                        this.validatorUtil.violations(c)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }

    @Override
    public Car getRandomCar() {
        long randomId = this.random.nextInt((int) this.carRepository.count()) + 1;
        return this.carRepository.getOne(randomId);
    }

    @Override
    public CarViewRootDto getCarsByMakeOrdered(String make) {
        CarViewRootDto carViewRootDto = new CarViewRootDto();

        List<CarViewDto> carViewDto = this.carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc(make)
                .stream()
                .map(c -> this.modelMapper.map(c, CarViewDto.class))
                .collect(Collectors.toList());

        carViewRootDto.setCarViewDtos(carViewDto);

        return carViewRootDto;
    }

    @Override
    public CarViewRootNoIdDto getAllCarsWithListOfParts() {
        CarViewRootNoIdDto carViewRootNoIdDto = new CarViewRootNoIdDto();

        List<CarViewNoIdDto> carViewNoIdDtos = this.carRepository.findAll()
                .stream()
                .map(c -> {
                    CarViewNoIdDto carViewNoIdDto = this.modelMapper.map(c, CarViewNoIdDto.class);

                    PartViewRootDto parts = new PartViewRootDto();

                    List<PartViewDto> partViewDtos = c.getParts()
                            .stream()
                            .map(p -> this.modelMapper.map(p,PartViewDto.class))
                            .collect(Collectors.toList());

                    parts.setParts(partViewDtos);

                    carViewNoIdDto.setParts(parts);

                    return carViewNoIdDto;
                })
                .collect(Collectors.toList());

        carViewRootNoIdDto.setCarViewNoIdDtos(carViewNoIdDtos);

        return carViewRootNoIdDto;
    }
}
