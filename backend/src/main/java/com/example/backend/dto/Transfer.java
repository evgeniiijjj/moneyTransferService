package com.example.backend.dto;

import com.example.backend.util.*;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
@TransferAmount(message = "INSUFFICIENT_SENDER_CARD_FUNDS")
@SenderCardCVV(message = "INVALID_SENDER_CARD_CVV")
@SenderCardValidTill(message = "INVALID_SENDER_CARD_VALID_TILL")
public class Transfer {
    @CardNumber(message = "INVALID_SENDER_CARD_NUMBER")
    private String cardFromNumber;
    @CardNumber(message = "INVALID_RECIPIENT_CARD_NUMBER")
    private String cardToNumber;
    private String cardFromCVV;
    private String cardFromValidTill;
    private Amount amount;
    
    public Transfer() {
        LOGGER.info(LoggerMessages.TRANSFER.getMessage(transfer));
    }
}
