package com.example.backend.util;

import com.example.backend.dto.Transfer;
import com.example.backend.entity.Card;
import com.example.backend.repo.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.example.backend.BackendApplication.LOGGER;

public class SenderCardCvvValidator implements ConstraintValidator<SenderCardCVV, Transfer> {

    private CardRepository repository;

    @Autowired
    public void initialize(CardRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean isValid(Transfer transfer, ConstraintValidatorContext context) {
        Card card = repository.getCard(transfer.getCardFromNumber());
        if (card == null) return true;
        boolean checkCardCvv = card.getCvv().equals(transfer.getCardFromCVV());
        if (checkCardCvv) LOGGER.info(LoggerMessages.CARD_CVV_VALID.getMessage(card.getCvv()));
        else LOGGER.info(LoggerMessages.CARD_CVV_INVALID.getMessage(card.getCvv()));
        return checkCardCvv;
    }


}
