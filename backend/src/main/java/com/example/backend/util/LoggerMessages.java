package com.example.backend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

public enum LoggerMessages {

    ADD_OPERATION ("Добавлена новая операция: %s"),
    CARD_REPO_INIT ("Репозиторий с картами инициализирован"),
    CARD_CVV_VALID ("CVV карты корректен: %s"),
    CARD_CVV_INVALID ("CVV карты некорректен: %s"),
    CARD_FUNDS_INSUFFICIENT ("На карте не достаточно средств для перевода"),
    CARD_FUNDS_SUFFICIENT ("На карте достаточно средств для перевода"),
    CARD_NUMBER_VALID ("Номер карты %s корректен"),
    CARD_NUMBER_INVALID ("Номер карты %s не корректен"),
    CARD_VALID_TILL_VALID ("Срок действия карты действителен: %s"),
    CARD_VALID_TILL_INVALID ("Срок действия карты действителен: %s"),
    CONFIRM_TRANSFER ("Инициализирован запрос на подтверждение операции перевода: %s"),
    EXCEPTION("Возникла внутренная ошибка сервиса, операция прервана: %s"),
    FAILED_CONFIRM_TRANSFER ("Неверный код подтверждения, операция отклонена"),
    FAILED_TRANSFER ("Операция отклонена, по причине наличия не валидных данных: %s"),
    SUCCESS_CONFIRM_TRANSFER ("Операция %s подтверждена"),
    SUCCESS_TRANSFER ("Операция %s принята, отправлен код подтверждение операции"),
    START ("Сервис перевода денег запущен"),
    TRANSFER ("Инициализован новый запрос на перевод: %s");

    private final String message;
    private final ObjectMapper mapper;

    LoggerMessages(String message) {
        this.message = message;
        this.mapper = new MappingJackson2HttpMessageConverter().getObjectMapper();
    }

    public String getMessage(Object... object) {
        if (object.length > 0) {
            try {
                return String.format(this.message, mapper.writeValueAsString(object[0]));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return this.message;
    }
}
