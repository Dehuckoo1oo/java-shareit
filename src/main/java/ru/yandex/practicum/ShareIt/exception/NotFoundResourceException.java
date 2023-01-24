package ru.yandex.practicum.ShareIt.exception;

import lombok.Getter;

@Getter
public class NotFoundResourseException extends RuntimeException {

    private final String parameter;

    public NotFoundResourseException(String parameter) {
        this.parameter = parameter;
    }
}
