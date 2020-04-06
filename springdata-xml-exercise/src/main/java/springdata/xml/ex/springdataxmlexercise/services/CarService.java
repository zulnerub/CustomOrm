package springdata.xml.ex.springdataxmlexercise.services;

import springdata.xml.ex.springdataxmlexercise.models.dtos.CarSeedDto;
import springdata.xml.ex.springdataxmlexercise.models.dtos.CarViewRootDto;
import springdata.xml.ex.springdataxmlexercise.models.dtos.CarViewRootNoIdDto;
import springdata.xml.ex.springdataxmlexercise.models.entities.Car;

import java.util.List;

public interface CarService {
    void seedCars(List<CarSeedDto> cars);

    Car getRandomCar();

    CarViewRootDto getCarsByMakeOrdered(String make);

    CarViewRootNoIdDto getAllCarsWithListOfParts();
}
