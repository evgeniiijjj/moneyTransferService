package com.example.backend.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class Account {
    private final String currency;
    private final String number;
    @Setter
    private int amount;

    public Account(String currency, String number) {
        this(currency, number, 0);
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }

    public void subtractAmount(int amount) {
        if (this.amount >= amount) {
            this.amount -= amount;
        }
    }
}
