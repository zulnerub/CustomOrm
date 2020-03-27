package springdata.json.exerone.productsshop.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springdata.json.exerone.productsshop.models.dtos.ProductSeedDto;
import springdata.json.exerone.productsshop.models.dtos.ProductsInRangeForSaleDto;
import springdata.json.exerone.productsshop.models.entities.Category;
import springdata.json.exerone.productsshop.models.entities.Product;
import springdata.json.exerone.productsshop.models.entities.User;
import springdata.json.exerone.productsshop.repositories.ProductRepository;
import springdata.json.exerone.productsshop.services.CategoryService;
import springdata.json.exerone.productsshop.services.ProductService;
import springdata.json.exerone.productsshop.services.UserService;
import springdata.json.exerone.productsshop.utils.ValidatorUtil;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, UserService userService, ModelMapper modelMapper, ValidatorUtil validatorUtil) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public void seedProducts(ProductSeedDto[] dtos) {
        if (this.productRepository.count() != 0){
            return;
        }
        Arrays.stream(dtos)
                .forEach(dto -> {
                    System.out.println();
                    if (this.validatorUtil.isValid(dto)){
                        Product product = this.modelMapper.map(dto, Product.class);
                        product.setSeller(this.userService.getRandomUser());
                        List<Category> categories = this.categoryService.getRandomCategories();
                        product.setCategories(new HashSet<>(categories));

                        if (this.productRepository.count() % 2 == 0){
                            long buyerId = this.userService.getRandomUser().getId();
                            while (product.getSeller().getId() == buyerId){
                                buyerId = this.userService.getRandomUser().getId();
                            }

                            product.setBuyer(this.userService.getUserById(buyerId));
                        }

                        this.productRepository.saveAndFlush(product);
                    }else {
                        this.validatorUtil.violations(dto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }

    @Override
    public List<ProductsInRangeForSaleDto> getAllProductsInRangeForSale() {


        return this.productRepository.findAllByPriceBetweenAndBuyerIsNull(
                BigDecimal.valueOf(500), BigDecimal.valueOf(1000))
                .stream()
                .map(p -> {
                    ProductsInRangeForSaleDto productsInRangeForSaleDto =
                            this.modelMapper.map(
                                    p, ProductsInRangeForSaleDto.class
                            );

                    productsInRangeForSaleDto
                            .setSeller(String.format("%s %s",
                                    p.getSeller().getFirstName(), p.getSeller().getLastName()));


                    return productsInRangeForSaleDto;
                })
                .collect(Collectors.toList());
    }
}
