package ru.yandex.practicum.ShareIt.exception;

import lombok.Getter;

@Getter
public class ConflictException extends RuntimeException {

    private final String parameter;

    public ConflictException(String parameter) {
        this.parameter = parameter;
    }
}
