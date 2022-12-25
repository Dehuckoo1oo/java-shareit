package ru.yandex.practicum.ShareIt.exception;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

    private final String parameter;

    public ValidationException(String parameter) {
        this.parameter = parameter;
    }
}