package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.models.dtos.PassengerSeedDto;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static softuni.exam.constants.GlobalConstants.PASSENGERS_FILE_PATH;

@Service
@Transactional
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final TownServiceImpl townService;

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson, TownServiceImpl townService) {
        this.passengerRepository = passengerRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.townService = townService;
    }


    @Override
    public Optional<Passenger> getPassengerByEmail(String email) {
        return this.passengerRepository.findByEmail(email);
    }

    @Override
    public boolean areImported() {
        return this.passengerRepository.count() > 0;
    }

    @Override
    public String readPassengersFileContent() throws IOException {
        return this.fileUtil.readFile(PASSENGERS_FILE_PATH);
    }

    @Override
    public String importPassengers() throws IOException {
        StringBuilder sb = new StringBuilder();
        System.out.println();
        PassengerSeedDto[] passengerSeedDto =
                this.gson.fromJson(this.readPassengersFileContent(), PassengerSeedDto[].class);

        Arrays.stream(passengerSeedDto)
                .forEach(p -> {
                    if (this.validationUtil.isValid(p)) {
                        Passenger passenger = this.modelMapper.map(p, Passenger.class);
                        if (this.passengerRepository.findByEmail(passenger.getEmail()).orElse(null) == null) {
                            Town town = this.townService.getTownByName(p.getTown()).orElse(null);
                            if (town != null) {
                                passenger.setTown(town);
                                sb.append(String.format("Successfully imported %s %s - %s",
                                        passenger.getClass().getSimpleName(), passenger.getLastName(), passenger.getEmail()));
                                this.passengerRepository.saveAndFlush(passenger);
                            } else {
                                sb.append("Invalid Passenger");
                            }
                        } else {
                            sb.append("Invalid Passenger");
                        }
                    } else {
                        sb.append("Invalid Passenger");
                    }
                    sb.append(System.lineSeparator());
                });


        return sb.toString();
    }

    @Override
    public String getPassengersOrderByTicketsCountDescendingThenByEmail() {
        StringBuilder sb = new StringBuilder();
        this.passengerRepository.findAllOrOrderByTicketsSizeDescEmailAsc()
                .forEach(p -> {
                    sb.append(String.format("Passenger %s  %s\n" +
                            "\tEmail - %s\n" +
                            "\tPhone - %s\n" +
                            "\tNumber of tickets - %d",
                            p.getFirstName(), p.getLastName(), p.getEmail(), p.getPhoneNumber(), p.getTickets().size()));
                    sb.append(System.lineSeparator());
                });


        return sb.toString();
    }
}
