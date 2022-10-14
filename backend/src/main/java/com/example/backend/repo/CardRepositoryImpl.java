package com.example.backend.repo;

import com.example.backend.dto.Amount;
import com.example.backend.entity.Card;
import com.example.backend.entity.Operation;
import com.example.backend.util.LoggerMessages;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.backend.BackendApplication.LOGGER;

@Repository
public class CardRepositoryImpl implements CardRepository {

    private final Map<Long, Operation> operations;

    private final Map<String, Card> cards;

    public CardRepositoryImpl() {
        operations = new ConcurrentHashMap<>();
        cards = new ConcurrentHashMap<>();
        cards.put(String.valueOf(1111_1111_1111_1111L), new Card(String.valueOf(1111_1111_1111_1111L), "01/31", "111"));
        cards.put(String.valueOf(2222_2222_2222_2222L), new Card(String.valueOf(2222_2222_2222_2222L), "02/32", "222"));
        cards.put(String.valueOf(3333_3333_3333_3333L), new Card(String.valueOf(3333_3333_3333_3333L), "03/33", "333"));
        cards.put(String.valueOf(4444_4444_4444_4444L), new Card(String.valueOf(4444_4444_4444_4444L), "04/34", "444"));
        cards.put(String.valueOf(5555_5555_5555_5555L), new Card(String.valueOf(5555_5555_5555_5555L), "05/35", "555"));
        LOGGER.info(LoggerMessages.CARD_REPO_INIT.getMessage());
    }

    @Override
    public Card getCard(String cardNumber) {
        return cards.get(cardNumber);
    }

    @Override
    public Operation getOperation(String operationId) {
        return operations.get(Long.parseLong(operationId));
    }

    @Override
    public boolean containsCardNumber(String cardNumber) {
        return cards.containsKey(cardNumber);
    }

    @Override
    public int getAvailableAmount(String cardNumber, String currency) {
        return cards.get(cardNumber).getAmount(currency);
    }

    @Override
    public String addOperation(String cardFromNumber, String cardToNumber, Amount amount) {
        Operation operation = new Operation(cards.get(cardFromNumber), cards.get(cardToNumber), amount.getCurrency(), amount.getValue());
        operations.put(operation.getOperationId(), operation);
        LOGGER.info(LoggerMessages.ADD_OPERATION.getMessage(operation));
        return String.valueOf(operation.getOperationId());
    }

}
