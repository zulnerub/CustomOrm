package springdata.json.exerone.productsshop.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import springdata.json.exerone.productsshop.models.dtos.CategorySeedDto;
import springdata.json.exerone.productsshop.models.dtos.ProductSeedDto;
import springdata.json.exerone.productsshop.models.dtos.ProductsInRangeForSaleDto;
import springdata.json.exerone.productsshop.models.dtos.UserSeedDto;
import springdata.json.exerone.productsshop.services.CategoryService;
import springdata.json.exerone.productsshop.services.ProductService;
import springdata.json.exerone.productsshop.services.UserService;
import springdata.json.exerone.productsshop.utils.FileIOUtil;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static springdata.json.exerone.productsshop.constants.GlobalConstants.*;

@Component
public class AppController implements CommandLineRunner {
    private final Gson gson;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final FileIOUtil fileIOUtil;

    @Autowired
    public AppController(Gson gson, CategoryService categoryService, UserService userService, ProductService productService, FileIOUtil fileIOUtil) {
        this.gson = gson;
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.fileIOUtil = fileIOUtil;
    }


    @Override
    public void run(String... args) throws Exception {

        this.seedCategories();
        this.seedUsers();
        this.seedProducts();

        this.writeProductInRangeForSale();

    }

    private void writeProductInRangeForSale() throws IOException {
        List<ProductsInRangeForSaleDto> productsInRangeForSaleDtos =
                this.productService.getAllProductsInRangeForSale();

        String json = this.gson.toJson(productsInRangeForSaleDtos);
        this.fileIOUtil.write(json, Q_1_OUTPUT);

        System.out.println();
    }

    private void seedProducts() throws FileNotFoundException {
        ProductSeedDto[] products = this.gson
                .fromJson(new FileReader(PRODUCTS_FILE_PATH),
                        ProductSeedDto[].class);

        this.productService.seedProducts(products);
    }

    private void seedUsers() throws FileNotFoundException {
        UserSeedDto[] dtos = this.gson
                .fromJson(new FileReader(USERS_FILE_PATH),
                        UserSeedDto[].class);

        this.userService.seedUsers(dtos);
    }

    private void seedCategories() throws FileNotFoundException {
        CategorySeedDto[] dtos = this.gson
                .fromJson(new FileReader(CATEGORIES_FILE_PATH),
                        CategorySeedDto[].class);

        this.categoryService.seedCategories(dtos);

        System.out.println();
    }
}
