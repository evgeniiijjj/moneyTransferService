package com.example.backend.util;

public enum ServiceMessages {

    INVALID_CONFIRMATION_CODE ("Неверный код подтверждения"),
    INVALID_RECIPIENT_CARD_NUMBER ("Неверный номер карты получателя"),
    INVALID_SENDER_CARD_CVV ("Неверный CVV-код карты отправителя"),
    INVALID_SENDER_CARD_VALID_TILL ("Неверный срок действия карты отправителя"),
    INVALID_SENDER_CARD_NUMBER ("Неверный номер карты отправителя"),
    INSUFFICIENT_SENDER_CARD_FUNDS ("Недостаточно средств на карте отправителя");

    private final String message;

    ServiceMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
