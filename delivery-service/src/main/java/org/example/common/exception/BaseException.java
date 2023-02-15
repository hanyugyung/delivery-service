package org.example.common.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private CustomErrorMessage errorMessage;

    public BaseException() {
    }

    public BaseException(CustomErrorMessage errorMessage) {
        super(errorMessage.getErrorMessage());
        this.errorMessage = errorMessage;
    }

    public BaseException(String message, CustomErrorMessage errorMessage) {
        super(message);
        this.errorMessage = errorMessage;
    }

    public BaseException(String message, CustomErrorMessage errorMessage, Throwable cause) {
        super(message, cause);
        this.errorMessage = errorMessage;
    }
}