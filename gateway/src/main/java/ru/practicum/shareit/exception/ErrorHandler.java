package ru.practicum.shareit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidBody(final UnsupportedStatusException e) {
        return new ErrorResponse(String.format("Unknown state: %s", e.getParameter()));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidBody(final MethodArgumentNotValidException e) {
        return new ErrorResponse(String.format("Ошибка валидации: %s", e.getParameter()));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNotFoundResource(final NotFoundResourceException e) {
        return new ErrorResponse(String.format("Ошибка валидации: %s", e.getParameter()));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundBody(final NoSuchBodyException e) {
        return new ErrorResponse(String.format("Запрашиваемый %s или не найден", e.getParameter()));
    }
}

