package alararestaurant.service;

import alararestaurant.domain.entities.Item;

import java.io.IOException;

public interface ItemService {
    Item getByName(String name);

    Boolean itemsAreImported();

    String readItemsJsonFile() throws IOException;

    String importItems(String items) throws IOException;
}
