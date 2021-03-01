package pl.bak.product.domain;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.bak.product.domain.dao.CreditRepository;
import pl.bak.product.domain.dao.ProductRepository;
import pl.bak.product.dto.ProductDto;
import pl.bak.product.model.Product;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CreditRepository creditRepository;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, CreditRepository creditRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.creditRepository = creditRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public ProductDto saveProduct(ProductDto productDto){
        Product product = modelMapper.map(productDto, Product.class);
        product.setId(null);

        int id = productDto.getCreditDto().getId();
        creditRepository.findById(id)
                .ifPresent(product::setCredit);

        Product save = productRepository.save(product);

        return modelMapper.map(save, ProductDto.class);
    }

    public List<ProductDto> getAll(List<Integer> creditId){
        List<Product> products = productRepository.findAllById(creditId);

        return products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }
}
