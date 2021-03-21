package ru.yakubov.vote.util.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorInfo {
    private final String url;
    private final HttpStatus status;
    private final String[] details;

    public ErrorInfo(CharSequence url, HttpStatus status, String[] details) {
        this.url = url.toString();
        this.status = status;
        this.details = details;
    }
}