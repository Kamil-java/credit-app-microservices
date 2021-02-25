package pl.bak.credit.domain;

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
import pl.bak.credit.app.body.test.BodyToTestProvider;
import pl.bak.credit.dto.MainDto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CreditController.class)
@AutoConfigureRestDocs(outputDir = "documentation/endpoints")
@AutoConfigureMockMvc
@Import(BodyToTestProvider.class)
class CreditControllerTest {

    @MockBean
    private CreditService creditService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BodyToTestProvider bodyToTestProvider;

    @Test
    void createCredit() throws Exception {
        //given
        given(creditService.createCredit(any(MainDto.class))).willReturn(Optional.ofNullable(bodyToTestProvider.prepareCredit()));

        //when
        String body = objectMapper.writeValueAsString(mainDto());
        ResultActions perform = mockMvc.perform(post("/credit/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        );

        //then
        perform
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.creditName").exists())
                .andExpect(jsonPath("$.creditName").isString())
                .andExpect(jsonPath("$.creditName").isNotEmpty())
                .andExpect(jsonPath("$.creditName").value("Credit-1"))
                .andDo(print())
                .andDo(document("create-credit"));


    }

    @Test
    void getCredits() throws Exception {
        //given
        given(creditService.getAll()).willReturn(List.of(mainDto()));

        //when
        ResultActions perform = mockMvc.perform(get("/credit/all")
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0].credit").exists())
                .andExpect(jsonPath("$.[0].credit").value(bodyToTestProvider.prepareCreditDto()))
                .andExpect(jsonPath("$.[0].customer").exists())
                .andExpect(jsonPath("$.[0].customer").value(bodyToTestProvider.prepareCustomerDto()))
                .andExpect(jsonPath("$.[0].product").exists())
                .andExpect(jsonPath("$.[0].product").value(bodyToTestProvider.prepareProductDto()))
                .andDo(print())
                .andDo(document("get-all-credit-with-details"));
    }

    private MainDto mainDto(){
        return bodyToTestProvider.prepareMainDto();
    }
}