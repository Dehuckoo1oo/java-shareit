package ru.yandex.practicum.ShareIt.exception;

import lombok.Getter;

@Getter
public class UnsupportedStatusException extends RuntimeException {

    private final String parameter;

    public UnsupportedStatusException(String parameter) {
        this.parameter = parameter;
    }
}
