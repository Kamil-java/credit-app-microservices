package pl.bak.customer.domain;

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
import pl.bak.customer.app.body.test.BodyToTestProvider;
import pl.bak.customer.dto.CustomerDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
@AutoConfigureRestDocs(outputDir = "documentation/endpoints")
@AutoConfigureMockMvc
@Import(BodyToTestProvider.class)
class CustomerControllerTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BodyToTestProvider bodyToTestProvider;

    @Test
    void shouldCreatedNewCustomer() throws Exception {
        //given
        CustomerDto customerDto = bodyToTestProvider.prepareCustomerDto();
        given(customerService.saveCustomer(customerDto)).willReturn(Optional.of(bodyToTestProvider.prepareCustomer()));

        //when
        String body = objectMapper.writeValueAsString(customerDto);
        ResultActions perform = mockMvc.perform(post("/customer/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        );

        //then
        perform
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").exists())
                .andExpect(jsonPath("$.firstName").isNotEmpty())
                .andExpect(jsonPath("$.firstName").isString())
                .andExpect(jsonPath("$.firstName").value("Customer-1"))
                .andExpect(jsonPath("$.surname").exists())
                .andExpect(jsonPath("$.surname").isNotEmpty())
                .andExpect(jsonPath("$.surname").isString())
                .andExpect(jsonPath("$.surname").value("C-Surname-1"))
                .andExpect(jsonPath("$.pesel").exists())
                .andExpect(jsonPath("$.pesel").isNotEmpty())
                .andExpect(jsonPath("$.pesel").isString())
                .andExpect(jsonPath("$.pesel").value("12345678910"))
                .andDo(print())
                .andDo(document("customer-save"));

    }

    @Test
    void ifCustomerByPeselExistShouldThrowStatusConflict() throws Exception {
        //given
        CustomerDto customerDto = bodyToTestProvider.prepareCustomerDto();
        given(customerService.saveCustomer(customerDto)).willReturn(Optional.empty());

        //when
        String body = objectMapper.writeValueAsString(customerDto);
        ResultActions perform = mockMvc.perform(post("/customer/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        );

        //then
        perform
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(print())
                .andDo(document("customer-conflict"));

    }

    @Test
    void getCustomers() throws Exception {
        //given
        given(customerService.getAll()).willReturn(List.of(bodyToTestProvider.prepareCustomerDto()));

        //when
        ResultActions perform = mockMvc.perform(get("/customer/all")
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0].id").doesNotExist())
                .andExpect(jsonPath("$.[0].firstName").exists())
                .andExpect(jsonPath("$.[0].firstName").isNotEmpty())
                .andExpect(jsonPath("$.[0].firstName").isString())
                .andExpect(jsonPath("$.[0].firstName").value("Customer-1"))
                .andExpect(jsonPath("$.[0].surname").exists())
                .andExpect(jsonPath("$.[0].surname").isNotEmpty())
                .andExpect(jsonPath("$.[0].surname").isString())
                .andExpect(jsonPath("$.[0].surname").value("C-Surname-1"))
                .andExpect(jsonPath("$.[0].pesel").exists())
                .andExpect(jsonPath("$.[0].pesel").isNotEmpty())
                .andExpect(jsonPath("$.[0].pesel").isString())
                .andExpect(jsonPath("$.[0].pesel").value("12345678910"))
                .andDo(print())
                .andDo(document("get-list-of-customer"));

    }

    @Test
    void shouldReturnHttpStatusNoContentIfListIsEmpty() throws Exception {
        //given
        given(customerService.getAll()).willReturn(Collections.emptyList());

        //when
        ResultActions perform = mockMvc.perform(get("/customer/all"));

        //then
        perform
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(print())
                .andDo(document("get-empty-list-of-customer"));

    }
}