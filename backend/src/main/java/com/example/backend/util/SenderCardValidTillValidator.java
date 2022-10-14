package com.example.backend.util;

import com.example.backend.dto.Transfer;
import com.example.backend.entity.Card;
import com.example.backend.repo.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.example.backend.BackendApplication.LOGGER;

public class SenderCardValidTillValidator implements ConstraintValidator<SenderCardValidTill, Transfer> {

    private CardRepository repository;

    @Autowired
    public void initialize(CardRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean isValid(Transfer transfer, ConstraintValidatorContext context) {
        Card card = repository.getCard(transfer.getCardFromNumber());
        if (card == null) return true;
        boolean checkCardValidTill = card.getCardValidTill().equals(transfer.getCardFromValidTill());
        if (checkCardValidTill) LOGGER.info(LoggerMessages.CARD_VALID_TILL_VALID.getMessage(card.getCardValidTill()));
        else LOGGER.info(LoggerMessages.CARD_VALID_TILL_INVALID.getMessage(card.getCardValidTill()));
        return checkCardValidTill;
    }
}
