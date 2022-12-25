package ru.yandex.practicum.ShareIt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidBody(final MethodArgumentNotValidException e) {
        return new ErrorResponse(String.format("Ошибка валидации: %s", e.getParameter()));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidBody(final ValidationException e) {
        return new ErrorResponse(String.format("Ошибка валидации: %s", e.getParameter()));
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundBody(final NoSuchBodyException e) {
        return new ErrorResponse(String.format("Запрашиваемый %s или не найден", e.getParameter()));
    }

    /*@ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse HandleException(final Throwable e) {
        return new ErrorResponse(String.format("Ошибка с телом %s", e.getMessage()));
    }*/

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleInvalidBody(final ConflictException e) {
        return new ErrorResponse(String.format("Возник конфликт данных: %s", e.getParameter()));
    }
}

class ErrorResponse {

    String error;

    public ErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
