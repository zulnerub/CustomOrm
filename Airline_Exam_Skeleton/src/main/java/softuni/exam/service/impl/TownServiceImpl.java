package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.models.dtos.TownSeedDto;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static softuni.exam.constants.GlobalConstants.TOWNS_FILE_PATH;

@Service
@Transactional
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public Optional<Town> getTownByName(String name) {
        return this.townRepository.findByName(name);
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return this.fileUtil.readFile(TOWNS_FILE_PATH);
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder sb = new StringBuilder();
        TownSeedDto[] townSeedDto =
                this.gson.fromJson(this.readTownsFileContent(), TownSeedDto[].class);
        System.out.println();
        Arrays.stream(townSeedDto)
                .forEach(t -> {
                    if (this.validationUtil.isValid(t)){
                        Town town = this.modelMapper.map(t, Town.class);
                        if (this.townRepository.findByName(town.getName()).orElse(null) == null){
                            sb.append(String.format("Successfully imported %s %s - %d",
                                    town.getClass().getSimpleName(), town.getName(), town.getPopulation()));

                            this.townRepository.saveAndFlush(town);
                        }else {
                            sb.append("Invalid Town");
                        }
                    }else{
                        sb.append("Invalid Town");
                    }
                    sb.append(System.lineSeparator());
                });
        return sb.toString();
    }
}
