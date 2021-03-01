package pl.bak.product.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.bak.product.app.body.test.BodyToTestProvider;
import pl.bak.product.dto.ProductDto;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@AutoConfigureRestDocs(outputDir = "documentation/endpoints")
@AutoConfigureMockMvc
@Import(BodyToTestProvider.class)
class ProductControllerTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BodyToTestProvider bodyToTestProvider;


    @Test
    void shouldCreateNewProduct() throws Exception {
        //given
        ProductDto value = bodyToTestProvider.prepareProductDto();
        given(productService.saveProduct(any(ProductDto.class))).willReturn(value);

        //when
        String body = objectMapper.writeValueAsString(value);

        ResultActions perform = mockMvc.perform(post("/product/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        );

        //then
        perform
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.productName").exists())
                .andExpect(jsonPath("$.productName").isString())
                .andExpect(jsonPath("$.productName").isNotEmpty())
                .andExpect(jsonPath("$.productName").value("Product-1"))
                .andExpect(jsonPath("$.value").exists())
                .andExpect(jsonPath("$.value").isNumber())
                .andExpect(jsonPath("$.value").isNotEmpty())
                .andExpect(jsonPath("$.value").value(200))
                .andDo(print())
                .andDo(document("create-product"));

    }

    @Test
    void shouldReturnSingletonListProductsDto() throws Exception {
        //given
        given(productService.getAll(List.of(1))).willReturn(List.of(bodyToTestProvider.prepareProductDto()));

        //when
        ResultActions perform = mockMvc.perform(get("/product/all")
                .contentType(MediaType.APPLICATION_JSON)
                .param("creditId", "1")
        );

        //then
        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0].id").exists())
                .andExpect(jsonPath("$.[0].id").isNotEmpty())
                .andExpect(jsonPath("$.[0].id").isNumber())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].productName").exists())
                .andExpect(jsonPath("$.[0].productName").isString())
                .andExpect(jsonPath("$.[0].productName").isNotEmpty())
                .andExpect(jsonPath("$.[0].productName").value("Product-1"))
                .andExpect(jsonPath("$.[0].value").exists())
                .andExpect(jsonPath("$.[0].value").isNumber())
                .andExpect(jsonPath("$.[0].value").isNotEmpty())
                .andExpect(jsonPath("$.[0].value").value(200))
                .andDo(print())
                .andDo(document("get-list-of-products"));

    }

    @Test
    void shouldReturnHttpStatusNoContentIfListIsEmpty() throws Exception {
        //given
        given(productService.getAll(List.of(1))).willReturn(Collections.emptyList());

        //when
        ResultActions perform = mockMvc.perform(get("/product/all")
                .param("creditId", "1")
        );

        //then
        perform
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(print())
                .andDo(document("get-empty-list-of-products"));

    }
}