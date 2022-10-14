package com.example.backend;

import com.example.backend.dto.Amount;
import com.example.backend.dto.ConfirmTransfer;
import com.example.backend.dto.Transfer;
import com.example.backend.entity.Operation;
import com.example.backend.repo.CardRepository;
import com.example.backend.util.ServiceMessages;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Field;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class BackendApplicationTests {

    private MockMvc mockMvc;
    private ObjectMapper mapper;
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private CardRepository repository;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        mapper = new MappingJackson2HttpMessageConverter().getObjectMapper();
    }

    @Test
    void transferTestIfIsNotError() throws Exception {
        String content = mapper.writeValueAsString(new Transfer(String.valueOf(1111_1111_1111_1111L)
                , String.valueOf(2222_2222_2222_2222L), "111"
                ,"01/31", new Amount("RUR", 3242)));

        mockMvc.perform(MockMvcRequestBuilders.post("/transfer").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.operationId").value("1"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void transferTestIfInvalidSenderCardNumber() throws Exception {
        String content = mapper.writeValueAsString(new Transfer(String.valueOf(1121_1111_1111_1111L)
                , String.valueOf(2222_2222_2222_2222L), "111"
                ,"01/31", new Amount("RUR", 324)));

        mockMvc.perform(MockMvcRequestBuilders.post("/transfer").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ServiceMessages.INVALID_SENDER_CARD_NUMBER.getMessage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void transferTestIfInvalidRecipientCardNumber() throws Exception {
        String content = mapper.writeValueAsString(new Transfer(String.valueOf(1111_1111_1111_1111L)
                , String.valueOf(2222_2212_2222_2222L), "111"
                ,"01/31", new Amount("RUR", 324)));

        mockMvc.perform(MockMvcRequestBuilders.post("/transfer").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ServiceMessages.INVALID_RECIPIENT_CARD_NUMBER.getMessage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void transferTestIfSenderCardInsufficientFunds() throws Exception {
        String content = mapper.writeValueAsString(new Transfer(String.valueOf(1111_1111_1111_1111L)
                , String.valueOf(2222_2222_2222_2222L), "111"
                ,"01/31", new Amount("RUR", 320000340)));

        mockMvc.perform(MockMvcRequestBuilders.post("/transfer").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ServiceMessages.INSUFFICIENT_SENDER_CARD_FUNDS.getMessage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void transferTestIfInvalidSenderCardCvv() throws Exception {
        String content = mapper.writeValueAsString(new Transfer(String.valueOf(1111_1111_1111_1111L)
                , String.valueOf(2222_2222_2222_2222L), "131"
                ,"01/31", new Amount("RUR", 32000)));

        mockMvc.perform(MockMvcRequestBuilders.post("/transfer").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ServiceMessages.INVALID_SENDER_CARD_CVV.getMessage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void transferTestIfInvalidSenderCardValidTill() throws Exception {
        String content = mapper.writeValueAsString(new Transfer(String.valueOf(1111_1111_1111_1111L)
                , String.valueOf(2222_2222_2222_2222L), "111"
                ,"03/31", new Amount("RUR", 32000)));

        mockMvc.perform(MockMvcRequestBuilders.post("/transfer").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ServiceMessages.INVALID_SENDER_CARD_VALID_TILL.getMessage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void confirmTransferIfIsNotError() throws Exception {
        Operation operation = mock(Operation.class);
        when(operation.confirm(0)).thenReturn(true);

        Field field = repository.getClass().getDeclaredField("operations");
        field.setAccessible(true);
        ((Map<Long, Operation>) field.get(repository)).put(1L, operation);

        String content = mapper.writeValueAsString(new ConfirmTransfer("1", "0000"));

        mockMvc.perform(MockMvcRequestBuilders.post("/confirmOperation").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.operationId").value("1"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void confirmTransferIfInvalidConfirmCode() throws Exception {
        Operation operation = mock(Operation.class);
        when(operation.confirm(0)).thenReturn(true);

        Field field = repository.getClass().getDeclaredField("operations");
        field.setAccessible(true);
        ((Map<Long, Operation>) field.get(repository)).put(1L, operation);

        String content = mapper.writeValueAsString(new ConfirmTransfer("1", "1234"));

        mockMvc.perform(MockMvcRequestBuilders.post("/confirmOperation").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ServiceMessages.INVALID_CONFIRMATION_CODE.getMessage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("2"))
                .andDo(MockMvcResultHandlers.print());
    }
}
