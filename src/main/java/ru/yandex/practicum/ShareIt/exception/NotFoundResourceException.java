package ru.yandex.practicum.ShareIt.exception;

import lombok.Getter;

@Getter
public class NotFoundResourceException extends RuntimeException {

    private final String parameter;

    public NotFoundResourceException(String parameter) {
        this.parameter = parameter;
    }
}
