package org.example.common.exception;

public class InvalidParamException extends BaseException {

    public InvalidParamException() {
        super(CustomErrorMessage.USER_ID_ALREADY_EXISTED);
    }

    public InvalidParamException(CustomErrorMessage errorMessage) {
        super(errorMessage);
    }

    public InvalidParamException(String errorMessage) {
        super(errorMessage, CustomErrorMessage.USER_ID_ALREADY_EXISTED);
    }

    public InvalidParamException(String errorMessage, CustomErrorMessage errorCode) {
        super(errorMessage, errorCode);
    }
}
