package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.models.dtos.PictureSeedDto;
import softuni.exam.models.entities.Car;
import softuni.exam.models.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.PictureService;
import softuni.exam.util.ValidationUtil;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static softuni.exam.constants.GlobalConstants.PICTURES_PATH;

@Service
@Transactional
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final CarService carService;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, CarService carService) {
        this.pictureRepository = pictureRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.carService = carService;
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        return Files.readString(Path.of(PICTURES_PATH));
    }

    @Override
    public String importPictures() throws IOException {
        StringBuilder sb = new StringBuilder();

        PictureSeedDto[] pictureSeedDtos = this.gson
                .fromJson(new FileReader(PICTURES_PATH), PictureSeedDto[].class);

        Arrays.stream(pictureSeedDtos)
                .forEach(pictureSeedDto -> {
                    if (this.validationUtil.isValid(pictureSeedDto)) {

                        Picture picture = this.modelMapper
                                .map(pictureSeedDto, Picture.class);

                        if (this.pictureRepository.getByName(pictureSeedDto.getName()).orElse(null) == null){
                            LocalDateTime localDateTime = LocalDateTime.parse(pictureSeedDto.getDateAndTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                            Car car = this.carService.getCarById((long) pictureSeedDto.getCar());
                            if (this.validationUtil.isValid(localDateTime) && this.validationUtil.isValid(car)){
                                picture.setCar(car);
                                picture.setDateAndTime(localDateTime);
                                sb.append("Successfully imported - ").append(picture.getName());
                                this.pictureRepository.saveAndFlush(picture);
                            }else {
                                sb.append("Invalid picture");
                            }
                        }else{
                            sb.append("Invalid picture");
                        }

                    } else {
                        sb.append("Invalid picture");
                    }
                    sb.append(System.lineSeparator());
                });

        return sb.toString();
    }
}
