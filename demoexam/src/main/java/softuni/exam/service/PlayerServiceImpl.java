package softuni.exam.service;


import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.PlayerSeedDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;


import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static softuni.exam.constants.GlobalConstants.PLAYERS_FILE_PATH;


@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {
    private final ValidatorUtil validatorUtil;
    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final Gson gson;
    private final TeamService teamService;
    private final PictureService pictureService;

    public PlayerServiceImpl(ValidatorUtil validatorUtil, PlayerRepository playerRepository, ModelMapper modelMapper, XmlParser xmlParser, Gson gson, TeamService teamService, PictureService pictureService) {
        this.validatorUtil = validatorUtil;
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.gson = gson;
        this.teamService = teamService;
        this.pictureService = pictureService;
    }


    @Override
    public String importPlayers() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        PlayerSeedDto[] dtos = this.gson
                .fromJson(new FileReader(PLAYERS_FILE_PATH), PlayerSeedDto[].class);

        Arrays.stream(dtos)
                .forEach(playerSeedDto -> {
                    if (this.validatorUtil.isValid(playerSeedDto)) {

                        Player player = this.modelMapper
                                .map(playerSeedDto, Player.class);

                        Team team = this.teamService.getTeamByName(playerSeedDto.getTeam().getName());

                        Picture picture = this.pictureService.getPictureByUrl(playerSeedDto.getPicture().getUrl());

                        if (team != null && picture != null) {
                            player.setPicture(picture);
                            player.setTeam(team);

                            sb.append("Successfully imported ").
                                    append(playerSeedDto.getFirstName())
                                    .append(" ")
                                    .append(playerSeedDto.getLastName());

                            this.playerRepository.saveAndFlush(player);
                        } else {
                            sb.append("Invalid Picture and/or team");
                        }
                    } else {
                        sb.append("Invalid player");
                    }
                    sb.append(System.lineSeparator());
                });


        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersJsonFile() throws IOException {
        return Files.readString(Path.of(PLAYERS_FILE_PATH));
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        StringBuilder sb = new StringBuilder();

        this.playerRepository.findAllBySalaryIsGreaterThanOrderBySalaryDesc(BigDecimal.valueOf(100000))
                .forEach(player -> {
                    sb.append(String.format("Player name: %s %s \n" +
                            "\tNumber: %d\n" +
                            "\tSalary: %.2f\n" +
                            "\tTeam: %s\n",
                            player.getFirstName(), player.getLastName(),
                            player.getNumber(), player.getSalary(), player.getTeam().getName()));
                    sb.append(System.lineSeparator());
                });



        return sb.toString();
    }

    @Override
    public String exportPlayersInATeam() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Team: North Hub%n"));

        this.playerRepository.findAllByTeamName("North Hub")
                .forEach(player -> {
                    sb.append(String.format("Player name: %s %s - %s\n" +
                                    "Number: %d%n", player.getFirstName(),
                            player.getLastName(),
                            player.getPosition(),
                            player.getNumber()));
                });


        return sb.toString();
    }


}
