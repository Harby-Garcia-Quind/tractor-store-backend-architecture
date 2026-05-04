package com.onlinecourses.identity.domain.model;

import com.onlinecourses.identity.domain.exception.InvalidEmailException;

public final class Email {

    private final String value;

    public Email(String value) {
        if (value == null) {
            throw new InvalidEmailException("El email no puede ser null");
        }

        String normalizedValue = value.trim().toLowerCase();

        if (normalizedValue.isBlank()) {
            throw new InvalidEmailException("El email no puede estar vacío");
        }

        if (!normalizedValue.contains("@")) {
            throw new InvalidEmailException("El email debe contener @");
        }

        this.value = normalizedValue;
    }

    public String value() {
        return value;
    }

}