package softuni.exam.service;


import softuni.exam.models.entities.Plane;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Optional;

public interface PlaneService {
    Optional<Plane> getPlaneByRegisterNumber(String regNum);

    boolean areImported();

    String readPlanesFileContent() throws IOException;
	
	String importPlanes() throws IOException, JAXBException;

}
