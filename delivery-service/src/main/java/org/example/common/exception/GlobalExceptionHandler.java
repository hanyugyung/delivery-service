package org.example.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.interfaces.user.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BaseException.class)
    public CommonResponse onException(BaseException exception) {
        return CommonResponse.fail(exception.getErrorMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse onException(MethodArgumentNotValidException exception) {
        return CommonResponse.fail(exception.getFieldError().getDefaultMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public CommonResponse onException(Exception exception) {
        log.error("exception={}", exception.getCause().getMessage());
        return CommonResponse.fail(exception.getMessage());
    }
}
