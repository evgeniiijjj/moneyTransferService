package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ConfirmTransfer {
    @Getter
    private final String operationId;
    private final String code;

    public int getCode() {
        return Integer.parseInt(code);
    }
}
