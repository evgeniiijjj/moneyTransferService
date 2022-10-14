package com.example.backend;

import com.example.backend.dto.*;
import com.example.backend.util.ServiceMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BackendApplicationContainerTests {
    @Autowired
    private TestRestTemplate restTemplate;
    @Container
    private GenericContainer<?> container;
    private String transferUrl;
    private String confirmOperationUrl;

    @BeforeEach
    void setUp() {
        container = new GenericContainer<>("moneytransferservice-backend");
        container.addExposedPort(8080);
        container.start();
        transferUrl = "http://localhost:" + container.getMappedPort(8080) + "/transfer";
        confirmOperationUrl = "http://localhost:" + container.getMappedPort(8080) + "/confirmOperation";
    }

    @Test
    void transferTestIfIsNotError() {
        ResponseEntity<ResponseSuccess> response = forTransferTestIfNotError();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().getOperationId());
    }

    private ResponseEntity<ResponseSuccess> forTransferTestIfNotError() {
        Transfer objectRequest = new Transfer(String.valueOf(1111_1111_1111_1111L)
                , String.valueOf(2222_2222_2222_2222L), "111"
                ,"01/31", new Amount("RUR", 3242));

        return restTemplate.postForEntity(transferUrl, objectRequest, ResponseSuccess.class);
    }

    @Test
    void transferTestIfInvalidSenderCardNumber() {
        Transfer objectRequest = new Transfer(String.valueOf(1121_1111_1111_1111L)
                , String.valueOf(2222_2222_2222_2222L), "111"
                ,"01/31", new Amount("RUR", 324));

        ResponseEntity<ResponseFailed> response = restTemplate.postForEntity(transferUrl, objectRequest, ResponseFailed.class);
        System.out.println(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ServiceMessages.INVALID_SENDER_CARD_NUMBER.getMessage(), response.getBody().getMessage());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    void transferTestIfInvalidRecipientCardNumber() {
        Transfer objectRequest = new Transfer(String.valueOf(1111_1111_1111_1111L)
                , String.valueOf(2222_2212_2222_2222L), "111"
                ,"01/31", new Amount("RUR", 324));

        ResponseEntity<ResponseFailed> response = restTemplate.postForEntity(transferUrl, objectRequest, ResponseFailed.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ServiceMessages.INVALID_RECIPIENT_CARD_NUMBER.getMessage(), response.getBody().getMessage());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    void transferTestIfSenderCardInsufficientFunds() {
        Transfer objectRequest = new Transfer(String.valueOf(1111_1111_1111_1111L)
                , String.valueOf(2222_2222_2222_2222L), "111"
                ,"01/31", new Amount("RUR", 320000340));

        ResponseEntity<ResponseFailed> response = restTemplate.postForEntity(transferUrl, objectRequest, ResponseFailed.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ServiceMessages.INSUFFICIENT_SENDER_CARD_FUNDS.getMessage(), response.getBody().getMessage());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    void transferTestIfInvalidSenderCardCvv() {
        Transfer objectRequest = new Transfer(String.valueOf(1111_1111_1111_1111L)
                , String.valueOf(2222_2222_2222_2222L), "131"
                ,"01/31", new Amount("RUR", 32000));

        ResponseEntity<ResponseFailed> response = restTemplate.postForEntity(transferUrl, objectRequest, ResponseFailed.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ServiceMessages.INVALID_SENDER_CARD_CVV.getMessage(), response.getBody().getMessage());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    void transferTestIfInvalidSenderCardValidTill() {
        Transfer objectRequest = new Transfer(String.valueOf(1111_1111_1111_1111L)
                , String.valueOf(2222_2222_2222_2222L), "131"
                ,"01/31", new Amount("RUR", 32000));

        ResponseEntity<ResponseFailed> response = restTemplate.postForEntity(transferUrl, objectRequest, ResponseFailed.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ServiceMessages.INVALID_SENDER_CARD_VALID_TILL.getMessage(), response.getBody().getMessage());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    void confirmTransferIfIsNotError() {
        forTransferTestIfNotError();
        ConfirmTransfer objectRequest = new ConfirmTransfer("1", "0000");

        ResponseEntity<ResponseSuccess> response = restTemplate.postForEntity(confirmOperationUrl, objectRequest, ResponseSuccess.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().getOperationId());
    }

    @Test
    void confirmTransferIfInvalidConfirmCode() {
        forTransferTestIfNotError();
        ConfirmTransfer objectRequest = new ConfirmTransfer("1", "1234");

        ResponseEntity<ResponseFailed> response = restTemplate.postForEntity(confirmOperationUrl, objectRequest, ResponseFailed.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ServiceMessages.INVALID_CONFIRMATION_CODE.getMessage(), response.getBody().getMessage());
        assertEquals(2, response.getBody().getId());
    }
}
