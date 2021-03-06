package ru.yakubov.vote.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "No data found")  // 406
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}