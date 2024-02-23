package com.example.taskspring.utils;

import java.util.Random;

public class PasswordGenerator {

    private final static char[] CHARACTERS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
            'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9'};

    public static String generatePassword(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Password n must be greater than zero.");
        }

        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            password.append(CHARACTERS[random.nextInt(CHARACTERS.length)]);
        }

        return password.toString();
    }

}
