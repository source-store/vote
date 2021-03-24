package ru.yakubov.vote.web;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yakubov.vote.util.ValidationUtil;
import ru.yakubov.vote.util.exception.ErrorInfo;
import ru.yakubov.vote.util.exception.FailVoteException;
import ru.yakubov.vote.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestErrorHandler {
    private static final Logger log = LoggerFactory.getLogger(RestErrorHandler.class);

    @ExceptionHandler(UnrecognizedPropertyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public Object UnrecognizedPropertyExceptionError(HttpServletRequest req, Exception e) {
        log.error("UnrecognizedPropertyException {} {}", req.getRequestURL(), e.getMessage());
        return logAndGetErrorInfo(req, e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FailVoteException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public Object FailVoteExceptionError(HttpServletRequest req, Exception e) {
        log.error("FailVoteException {} {}", req.getRequestURL(), e.getMessage());
        return logAndGetErrorInfo(req, e, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Object UsernameNotFoundExceptionError(HttpServletRequest req, Exception e) {
        log.error("UsernameNotFoundException {} {}", req.getRequestURL(), e.getMessage());
        return logAndGetErrorInfo(req, e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Object NotFoundExceptionError(HttpServletRequest req, Exception e) {
        log.error("NotFoundException {} {}", req.getRequestURL(), e.getMessage());
        return logAndGetErrorInfo(req, e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Object DataIntegrityViolationExceptionError(HttpServletRequest req, Exception e) {
        log.error("DataIntegrityViolationException {} {}", req.getRequestURL(), e.getMessage());
        return logAndGetErrorInfo(req, e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Object MethodArgumentNotValidExceptionError(HttpServletRequest req, Exception e) {
        log.error("MethodArgumentNotValidException {} {}", req.getRequestURL(), e.getMessage());
        return logAndGetErrorInfo(req, e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Object HttpMessageNotReadableExceptionError(HttpServletRequest req, Exception e) {
        log.error("HttpMessageNotReadableException {} {}", req.getRequestURL(), e.getMessage());
        return logAndGetErrorInfo(req, e, HttpStatus.BAD_REQUEST);
    }

    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, HttpStatus status, String... details) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        log.warn("{} at request  {}: {}", status, req.getRequestURL(), rootCause.toString());
        if (details.length == 0){
            details = rootCause != null ? new String[]{rootCause.getLocalizedMessage()} : new String[]{rootCause.getClass().getName()};
        }
        return new ErrorInfo(req.getRequestURL(), status, details);
    }
}
