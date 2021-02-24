package pl.bak.product.app.body.test;

import org.springframework.stereotype.Component;
import pl.bak.product.dto.CreditDto;
import pl.bak.product.dto.ProductDto;
import pl.bak.product.model.Credit;
import pl.bak.product.model.Product;

@Component
public class BodyToTestProvider {

    public Credit prepareCredit() {
        Credit credit = new Credit();

        credit.setId(1);
        credit.setCreditName("Credit-1");

        return credit;
    }

    public Product prepareProduct() {
        Product product = new Product();

        product.setId(1);
        product.setProductName("Product-1");
        product.setValue(200);

        return product;
    }

    public CreditDto prepareCreditDto() {
        CreditDto creditDto = new CreditDto();

        creditDto.setId(1);
        creditDto.setCreditName("Credit-1");

        return creditDto;
    }

    public ProductDto prepareProductDto() {
        ProductDto productDto = new ProductDto();

        productDto.setId(1);
        productDto.setProductName("Product-1");
        productDto.setValue(200);
        productDto.setCreditDto(prepareCreditDto());

        return productDto;
    }
}
