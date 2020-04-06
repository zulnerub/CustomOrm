package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.common.GlobalConstants;
import hiberspring.domain.dtos.CardSeedDto;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.repository.EmployeeCardRepository;
import hiberspring.service.EmployeeCardService;
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

import static hiberspring.common.GlobalConstants.EMPLOYEE_CARDS_FILE_PATH;

@Service
@Transactional
public class EmployeeCardServiceImpl implements EmployeeCardService {
        private final EmployeeCardRepository employeeCardRepository;
        private final Gson gson;
        private final ModelMapper modelMapper;
        private final ValidationUtil validationUtil;

    public EmployeeCardServiceImpl(EmployeeCardRepository employeeCardRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.employeeCardRepository = employeeCardRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public EmployeeCard getByNumber(String number) {
        return this.employeeCardRepository.findByNumber(number).orElse(null);
    }

    @Override
    public Boolean employeeCardsAreImported() {
        return this.employeeCardRepository.count() > 0;
    }

    @Override
    public String readEmployeeCardsJsonFile() throws IOException {
        return Files.readString(Path.of(EMPLOYEE_CARDS_FILE_PATH));
    }

    @Override
    public String importEmployeeCards(String employeeCardsFileContent) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        CardSeedDto[] cardSeedDto =
                this.gson.fromJson(new FileReader(EMPLOYEE_CARDS_FILE_PATH), CardSeedDto[].class);

        Arrays.stream(cardSeedDto)
                .forEach(c -> {
                    if (this.employeeCardRepository.findByNumber(c.getNumber()).orElse(null) == null){
                        if (this.validationUtil.isValid(c)){
                            EmployeeCard employeeCard = this.modelMapper.map(c, EmployeeCard.class);
                            sb.append(String.format("Successfully imported Employee Card %s.", employeeCard.getNumber()));

                            this.employeeCardRepository.saveAndFlush(employeeCard);
                        }else {
                            sb.append("ERROR: Invalid data.");
                        }
                    }else {
                        sb.append("ERROR: Invalid data.");
                    }
                    sb.append(System.lineSeparator());
                });





        return sb.toString();
    }
}
