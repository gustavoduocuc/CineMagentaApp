package com.cinemagenta.utils;

public class InputValidator {
    private static final String VALID_NAME_PATTERN = "^[\\p{L}0-9 .:'\\-ñÑáéíóúÁÉÍÓÚ]+$";

    public static boolean isValidName(String input) {
        return input != null && input.matches(VALID_NAME_PATTERN);
    }
}