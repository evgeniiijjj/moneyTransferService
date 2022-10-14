package com.example.backend.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicLong;

@EqualsAndHashCode
@Getter
public class Operation {

    private static final AtomicLong id = new AtomicLong();

    private final long operationId;
    private final Card cardFrom;
    private final Card cardTo;
    private final String currency;
    private final int amount;
    private final int verificationCode;
    private boolean confirmed;

    public Operation(Card cardFrom, Card cardTo, String currency, int amount) {
        this.operationId = id.addAndGet(1);
        this.cardFrom = cardFrom;
        this.cardTo = cardTo;
        this.currency = currency;
        this.amount = amount;
        this.verificationCode = 0;
    }

    public boolean confirm(int verificationCode) {
        if (this.verificationCode == verificationCode) {
            cardFrom.getAccount(currency).subtractAmount(amount);
            cardTo.getAccount(currency).addAmount(amount);
            confirmed = true;
        }
        return confirmed;
    }
}
