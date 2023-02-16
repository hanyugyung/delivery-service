package org.example.common.exception;

import lombok.Getter;

@Getter
public enum CustomErrorMessage {

    USER_ID_ALREADY_EXISTED("이미 존재하는 계정입니다.")
    ;

    private final String errorMessage;

    CustomErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
