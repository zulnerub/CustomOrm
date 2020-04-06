package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.models.dtos.OfferSeedRootDto;
import softuni.exam.models.entities.Car;
import softuni.exam.models.entities.Offer;
import softuni.exam.models.entities.Seller;
import softuni.exam.models.enums.Rating;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.OfferService;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static softuni.exam.constants.GlobalConstants.OFFERS_PATH;

@Service
@Transactional
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final SellerService sellerService;
    private final CarService carService;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, SellerService sellerService, CarService carService) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.sellerService = sellerService;
        this.carService = carService;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(OFFERS_PATH));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        OfferSeedRootDto offerSeedRootDto =
                this.xmlParser.importFromXml(OfferSeedRootDto.class, OFFERS_PATH);

        offerSeedRootDto.getOffers()
                .forEach(o -> {
                    Offer offer = this.modelMapper.map(o, Offer.class);
                    if (this.validationUtil.isValid(offer)) {
                        LocalDateTime addedOn = LocalDateTime.parse(o.getAddedOn(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        if (this.offerRepository.findByDescriptionAndAddedOn(o.getDescription(), addedOn) == null
                                && this.carService.getCarById(o.getCar().getId()) != null
                                && this.sellerService.getById(o.getSeller().getId()) != null) {

                            offer.setAddedOn(addedOn);

                            Car car = this.carService.getCarById(o.getCar().getId());

                            Seller seller = this.sellerService.getById(o.getSeller().getId());

                            if (this.validationUtil.isValid(car) && this.validationUtil.isValid(seller) && car != null && seller != null) {
                                offer.setCar(car);
                                offer.setSeller(seller);

                                sb.append(String.format("Successfully import offer %s - %s", offer.getAddedOn().toString(), offer.isHasGoldStatus()));

                                this.offerRepository.saveAndFlush(offer);
                            } else {
                                sb.append("Invalid offer");
                            }
                        } else {
                            sb.append("Invalid offer");
                        }
                    } else {
                        sb.append("Invalid offer");
                    }
                    sb.append(System.lineSeparator());
                });


        return sb.toString();
    }
}
