package hiberspring.service;

//TODO

import hiberspring.domain.entities.Town;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface TownService {
    Town getByName(String name);

    Boolean townsAreImported();

    String readTownsJsonFile() throws IOException;

    String importTowns(String townsFileContent) throws FileNotFoundException;

}
