package softuni.exam.service;

import softuni.exam.models.entities.Seller;
import softuni.exam.models.enums.Rating;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface SellerService {
    
    boolean areImported();

    String readSellersFromFile() throws IOException;

    String importSellers() throws IOException, JAXBException;

    boolean isRatingValid(String enumValue);

    Seller getById(Long id);

}
