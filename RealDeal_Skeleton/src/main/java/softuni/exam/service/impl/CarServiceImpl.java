package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.models.dtos.CarSeedDto;
import softuni.exam.models.entities.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtil;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static softuni.exam.constants.GlobalConstants.CARS_PATH;

@Service
@Transactional
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.carRepository = carRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }


    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        return Files.readString(Path.of(CARS_PATH));
    }

    @Override
    public String importCars() throws IOException {
        StringBuilder sb = new StringBuilder();

        CarSeedDto[] cars = this.gson
                .fromJson(new FileReader(CARS_PATH), CarSeedDto[].class);

        Arrays.stream(cars)
                .forEach(carSeedDto -> {
                    if (this.validationUtil.isValid(carSeedDto)) {

                        Car car = this.modelMapper
                                .map(carSeedDto, Car.class);
                    if (this.carRepository.findByMakeAndModelAndKilometers(car.getMake(), car.getModel(), car.getKilometers()).orElse(null) == null){
                        sb.append("Successfully imported - ").append(car.getMake()).append(" - ").append(car.getModel());

                        car.setRegisteredOn(LocalDate.parse(carSeedDto.getRegisteredOn(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));

                        this.carRepository.saveAndFlush(car);
                    }else{
                        sb.append("Invalid car");
                    }

                    } else {
                        sb.append("Invalid car");
                    }
                    sb.append(System.lineSeparator());
                });

        return sb.toString();
    }

    @Override
    public Car getCarById(Long id) {
        return this.carRepository.findById(id).orElse(null);
    }

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {
        StringBuilder sb = new StringBuilder();

        this.carRepository.findAllOrderByPicturesSizeDescAndMakeAsc()
                .forEach(c -> {
                    String str = String.format("Car make - %s, model - %s\n" +
                            "\tKilometers - %d\n" +
                            "\tRegistered on - %s\n" +
                            "\tNumber of pictures - %d",
                            c.getMake(), c.getModel(), c.getKilometers(), c.getRegisteredOn().toString(), c.getPictures().size());
                    sb.append(str).append(System.lineSeparator());
                });


        return sb.toString();
    }
}
