package com.example.backend.entity;

import com.example.backend.util.Util;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@Getter
public class Card {
    private final String cardNumber;
    private final String cardValidTill;
    private final String cvv;
    private Map<String, Account> accounts;

    public Card(String cardNumber, String cardFromValidTill, String cvv){
        this(cardNumber, cardFromValidTill, cvv, new HashMap<>());
        Account account = new Account("RUR", Util.numberGenerator(20), Util.accountAmountGenerator(10_000_000, 100_000_000));
        accounts.put("RUR", account);
    }

    public Account getAccount(String currency) {
        return accounts.get(currency);
    }

    public int getAmount(String currency) {
        return accounts.get(currency).getAmount();
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, cardValidTill, cvv);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Card) {
            Card card = (Card) obj;
            return this.cardNumber.equals(card.cardNumber)
                    && this.cardValidTill.equals(card.cardValidTill)
                    && this.cvv == card.cvv;
        }
        return false;
    }
}
