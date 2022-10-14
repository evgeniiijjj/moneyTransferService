package com.example.backend.util;

import com.example.backend.repo.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.example.backend.BackendApplication.LOGGER;

public class CardNumberValidator implements ConstraintValidator<CardNumber, String> {

    private CardRepository repository;

    @Autowired
    public void initialize(CardRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean check = repository.containsCardNumber(value);
        if (check) LOGGER.info(LoggerMessages.CARD_NUMBER_VALID.getMessage(value));
        else LOGGER.info(LoggerMessages.CARD_NUMBER_INVALID.getMessage());
        return check;
    }
}
