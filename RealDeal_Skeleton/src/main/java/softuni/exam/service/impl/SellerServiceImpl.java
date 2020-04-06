package softuni.exam.service.impl;

import com.fasterxml.jackson.databind.util.EnumValues;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.models.dtos.SellerSeedDto;
import softuni.exam.models.dtos.SellerSeedRootDto;
import softuni.exam.models.entities.Seller;
import softuni.exam.models.enums.Rating;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

import static softuni.exam.constants.GlobalConstants.SELLERS_PATH;

@Service
@Transactional
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.sellerRepository = sellerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.sellerRepository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return Files.readString(Path.of(SELLERS_PATH));
    }

    @Override
    public String importSellers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        SellerSeedRootDto sellerSeedRootDto =
                this.xmlParser.importFromXml(SellerSeedRootDto.class, SELLERS_PATH);

        sellerSeedRootDto.getSellers()
                .forEach(s -> {
                    Seller seller = this.modelMapper.map(s, Seller.class);
                    if (this.validationUtil.isValid(seller)) {
                        if (this.sellerRepository.findByEmail(s.getEmail()) == null) {
                            if (this.isRatingValid(s.getRating())) {
                                seller.setRating(Rating.valueOf(s.getRating()));
                                sb.append(String.format("Successfully imported seller %s - %s", seller.getLastName(), seller.getEmail()));
                                this.sellerRepository.saveAndFlush(seller);
                            } else {
                                sb.append("Invalid seller");
                            }
                        } else {
                            sb.append("Invalid seller");
                        }
                    } else {
                        sb.append("Invalid seller");
                    }
                    sb.append(System.lineSeparator());
                });


        return sb.toString();
    }

    @Override
    public boolean isRatingValid(String enumValue) {
        return Arrays.stream(Rating.values())
                .map(Enum::toString)
                .collect(Collectors.toList())
                .contains(enumValue);
    }

    @Override
    public Seller getById(Long id) {
        return this.sellerRepository.findById(id).orElse(null);
    }
}
