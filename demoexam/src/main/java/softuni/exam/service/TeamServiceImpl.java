package softuni.exam.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.TeamSeedRootDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;


import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.constants.GlobalConstants.TEAMS_FILE_PATH;


@Service
@Transactional
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final PictureService pictureService;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, PictureService pictureService, ModelMapper modelMapper, XmlParser xmlParser, ValidatorUtil validatorUtil) {
        this.teamRepository = teamRepository;
        this.pictureService = pictureService;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validatorUtil = validatorUtil;
    }


    @Override

    public String importTeams() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        TeamSeedRootDto teamSeedRootDto =
                this.xmlParser.importFromXml(TeamSeedRootDto.class, TEAMS_FILE_PATH);

        teamSeedRootDto.getTeamSeedDtos()
                .forEach(teamSeedDto -> {
                    if (this.validatorUtil.isValid(teamSeedDto)) {
                        Team team = this.modelMapper
                                .map(teamSeedDto, Team.class);

                        Picture picture = this.pictureService.getPictureByUrl(teamSeedDto.getPicture().getUrl());
                        if (picture != null){
                            team.setPicture(picture);

                            sb.append("Successfully imported ").append(team.getName());

                            this.teamRepository.saveAndFlush(team);
                        }else{
                            sb.append("Invalid Team");
                        }


                    } else {
                        sb.append("Invalid Team");
                    }
                    sb.append(System.lineSeparator());
                });


        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsXmlFile() throws IOException {
        return Files.readString(Path.of(TEAMS_FILE_PATH));
    }

    @Override
    public Team getTeamByName(String name) {
        return this.teamRepository.findByName(name);
    }

}
