package softuni.exam.service;


import softuni.exam.models.entities.Town;

import java.io.IOException;
import java.util.Optional;

public interface TownService {
    Optional<Town> getTownByName(String name);

    boolean areImported();

    String readTownsFileContent() throws IOException;
	
	String importTowns() throws IOException;
}
