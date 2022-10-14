package com.example.backend.util;

import com.example.backend.dto.Transfer;
import com.example.backend.repo.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.example.backend.BackendApplication.LOGGER;

public class TransferAmountValidator implements ConstraintValidator<TransferAmount, Transfer> {

    private CardRepository repository;

    @Autowired
    public void initialize(CardRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean isValid(Transfer transfer, ConstraintValidatorContext context) {
        if (!repository.containsCardNumber(transfer.getCardFromNumber())) return true;
        boolean check = repository.getAvailableAmount(transfer.getCardFromNumber(), transfer.getAmount().getCurrency()) >= transfer.getAmount().getValue();
        if (check) LOGGER.info(LoggerMessages.CARD_FUNDS_SUFFICIENT.getMessage(transfer.getAmount()));
        else LOGGER.info(LoggerMessages.CARD_FUNDS_INSUFFICIENT.getMessage(transfer.getAmount()));
        return check;
    }
}
