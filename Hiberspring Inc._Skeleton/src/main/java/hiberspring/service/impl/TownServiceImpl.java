package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.domain.dtos.TownSeedDto;
import hiberspring.domain.entities.Town;
import hiberspring.repository.TownRepository;
import hiberspring.service.TownService;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static hiberspring.common.GlobalConstants.TOWNS_FILE_PATH;

@Service
@Transactional
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }


    @Override
    public Town getByName(String name) {
        return this.townRepository.findByName(name).orElse(null);
    }

    @Override
    public Boolean townsAreImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        return Files.readString(Path.of(TOWNS_FILE_PATH));
    }

    @Override
    public String importTowns(String townsFileContent) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        TownSeedDto[] townSeedDto = this.gson.fromJson(new FileReader(TOWNS_FILE_PATH), TownSeedDto[].class);

        Arrays.stream(townSeedDto)
                .forEach(t -> {
            if (this.townRepository.findByName(t.getName()).orElse(null) == null){
                if (this.validationUtil.isValid(t)){
                    Town town = this.modelMapper.map(t, Town.class);

                    sb.append(String.format("Successfully imported %s %s.",
                            town.getClass().getSimpleName(), town.getName()));


                    this.townRepository.saveAndFlush(town);
                }else {
                sb.append("Error: Invalid data.");
                }
            }else {
                sb.append("Error: Invalid data.");

            }

            sb.append(System.lineSeparator());
        });




        return sb.toString();
    }
}
