package springdata.json.exerone.productsshop.services;

import springdata.json.exerone.productsshop.models.dtos.ProductSeedDto;
import springdata.json.exerone.productsshop.models.dtos.ProductsInRangeForSaleDto;

import java.util.List;

public interface ProductService {
    void seedProducts(ProductSeedDto[] productSeedDtos);

    List<ProductsInRangeForSaleDto> getAllProductsInRangeForSale();

}
