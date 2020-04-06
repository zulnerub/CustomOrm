package springdata.xml.ex.springdataxmlexercise.services;

import springdata.xml.ex.springdataxmlexercise.models.dtos.PartSeedDto;
import springdata.xml.ex.springdataxmlexercise.models.entities.Part;

import java.util.List;

public interface PartService {
    void seedParts(List<PartSeedDto> parts);

    Part getRandomPart();
}
