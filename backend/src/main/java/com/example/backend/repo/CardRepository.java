package com.example.backend.repo;

import com.example.backend.dto.Amount;
import com.example.backend.entity.Card;
import com.example.backend.entity.Operation;

public interface CardRepository {
    Card getCard(String cardNumber);
    Operation getOperation(String operationId);
    String addOperation(String cardFromNumber, String cardToNumber, Amount amount);
    boolean containsCardNumber(String cardNumber);
    int getAvailableAmount(String cardNumber, String currency);
}
