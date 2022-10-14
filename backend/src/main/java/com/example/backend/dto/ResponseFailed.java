package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseFailed {
    private String message;
    private int id;

    @Override
    public String toString() {
        return "{\"message\": \"" + message
                + "\", \"id\": " + id
                + "}";
    }
}
