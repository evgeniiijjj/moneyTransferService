package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ConfirmTransfer {
    private final String operationId;
    private final String code;

    public int getVerificationCode() {
        return Integer.parseInt(code);
    }
}
