package com.example.backend.util;

import java.util.Random;

public class Util {

    public static String numberGenerator(int lengthNumber) {
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < lengthNumber; i++) {
            sb.append(rnd.nextInt(9));
        }
        return sb.toString();
    }

    public static int accountAmountGenerator(int min, int bound) {
        Random rnd = new Random();
        return min + rnd.nextInt(bound);
    }
}
