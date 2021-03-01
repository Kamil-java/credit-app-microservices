package pl.bak.product.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import pl.bak.product.app.body.test.BodyToTestProvider;
import pl.bak.product.domain.dao.CreditRepository;
import pl.bak.product.domain.dao.ProductRepository;
import pl.bak.product.dto.ProductDto;
import pl.bak.product.model.Product;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CreditRepository creditRepository;

    private ProductService productService;

    private BodyToTestProvider bodyToTestProvider;

    @BeforeEach
    void setUp() {
        bodyToTestProvider = new BodyToTestProvider();

        ModelMapper modelMapper = new ModelMapper();
        productService = new ProductService(productRepository, creditRepository, modelMapper);
    }

    @Test
    void shouldSaveProduct() {
        //given
        given(creditRepository.findById(1)).willReturn(Optional.of(bodyToTestProvider.prepareCredit()));
        given(productRepository.save(any(Product.class))).willReturn(bodyToTestProvider.prepareProduct());

        //when
        ProductDto productDto = bodyToTestProvider.prepareProductDto();
        ProductDto saveProduct = productService.saveProduct(productDto);

        //then
        verify(creditRepository).findById(1);
        assertThat(saveProduct)
                .isNotNull()
                .isNotEqualTo(productDto)
                .hasFieldOrProperty("id")
                .hasFieldOrProperty("productName")
                .hasFieldOrProperty("value");
    }

    @Test
    void shouldReturnAllProducts() {
        //given
        List<Integer> creditId = List.of(1);
        given(productRepository.findAllById(creditId)).willReturn(List.of(bodyToTestProvider.prepareProduct()));

        //when
        List<ProductDto> allProducts = productService.getAll(creditId);

        //then
        assertThat(allProducts)
                .isNotEmpty()
                .hasSize(1);
        assertThat(allProducts.get(0))
                .isNotNull()
                .isNotEqualTo(bodyToTestProvider.prepareProduct())
                .hasFieldOrProperty("productName")
                .hasFieldOrProperty("value");
    }
}