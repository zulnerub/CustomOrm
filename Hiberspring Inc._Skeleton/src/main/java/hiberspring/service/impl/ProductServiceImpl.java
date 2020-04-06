package hiberspring.service.impl;

import hiberspring.domain.dtos.ProductRootSeedDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Product;
import hiberspring.repository.ProductRepository;
import hiberspring.service.BranchService;
import hiberspring.service.ProductService;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static hiberspring.common.GlobalConstants.PRODUCTS_FILE_PATH;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final XmlParser xmlParser;
    private final BranchService branchService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;


    public ProductServiceImpl(ProductRepository productRepository, XmlParser xmlParser, BranchService branchService, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.productRepository = productRepository;
        this.xmlParser = xmlParser;
        this.branchService = branchService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }


    @Override
    public Boolean productsAreImported() {
        return this.productRepository.count() > 0;
    }

    @Override
    public String readProductsXmlFile() throws IOException {
        return Files.readString(Path.of(PRODUCTS_FILE_PATH));
    }

    @Override
    public String importProducts() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        ProductRootSeedDto productRootSeedDto = this.xmlParser.parseXml(ProductRootSeedDto.class, PRODUCTS_FILE_PATH);

        productRootSeedDto.getProducts()
                .forEach(p -> {
                    if (this.validationUtil.isValid(p)){
                        if (this.productRepository.findByName(p.getName()).orElse(null) == null){
                            if (this.branchService.getByName(p.getBranch()) != null){
                                Product product = this.modelMapper.map(p, Product.class);
                                Branch branch = this.branchService.getByName(p.getBranch());

                                product.setBranch(branch);

                                sb.append(String.format("Successfully imported %s %s", product.getClass().getSimpleName(), product.getName()));

                                this.productRepository.saveAndFlush(product);
                            }else {
                                sb.append("Error: Invalid data.");
                            }
                        }else {
                            sb.append("Error: Invalid data.");
                        }
                    }else {
                        sb.append("Error: Invalid data.");
                    }
                    sb.append(System.lineSeparator());
                });
        return sb.toString();
    }
}
