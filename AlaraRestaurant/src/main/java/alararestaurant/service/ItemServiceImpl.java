package alararestaurant.service;

import alararestaurant.domain.dtos.ItemsSeedDto;
import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;

import static alararestaurant.constants.GlobalConstants.ITEMS_FILE_PATH;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final FileUtil fileUtil;
    private final CategoryRepository categoryRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, FileUtil fileUtil, CategoryRepository categoryRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.itemRepository = itemRepository;
        this.fileUtil = fileUtil;
        this.categoryRepository = categoryRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Item getByName(String name) {
        return this.itemRepository.findByName(name).orElse(null);
    }

    @Override
    public Boolean itemsAreImported() {
        return this.itemRepository.count() > 0;
    }

    @Override
    public String readItemsJsonFile() throws IOException {
        return this.fileUtil.readFile(ITEMS_FILE_PATH);
    }

    @Override
    public String importItems(String items) throws IOException {
        StringBuilder sb = new StringBuilder();
        ItemsSeedDto[] itemsSeedDtos = this.gson.fromJson(this.fileUtil.readFile(ITEMS_FILE_PATH), ItemsSeedDto[].class);

        Arrays.stream(itemsSeedDtos)
                .forEach(i -> {
                    if (this.validationUtil.isValid(i)
                        && this.itemRepository.findByName(i.getName()).orElse(null) == null){
                        if (this.categoryRepository.findByName(i.getCategory()).orElse(null) == null){
                            this.categoryRepository.saveAndFlush(new Category(i.getCategory()));
                        }
                        Item item = this.modelMapper.map(i, Item.class);
                        item.setCategory(this.categoryRepository.findByName(i.getCategory()).get());

                        sb.append(String.format("Record %s successfully imported.", item.getName()));

                        this.itemRepository.saveAndFlush(item);
                    }else{
                        sb.append("Invalid data format.");
                    }
                    sb.append(System.lineSeparator());
                });


        return sb.toString();
    }
}
