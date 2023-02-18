package org.example.common.exception;

import lombok.Getter;

@Getter
public enum CustomErrorMessage {

    USER_ID_ALREADY_EXISTED("이미 존재하는 계정입니다.")
    , USER_FAIL_LOGIN("로그인에 실패하였습니다.")
    , USER_FAIL_AUTHORIZATION("인증이 필요합니다.")
    , DELIVERY_HAS_ALREADY_STARTED("이미 배송이 시작되었습니다.")
    , DELIVERY_STATUS_INVALID("유효하지 않은 배송 상태입니다.")
    , DELIVERY_SEARCH_TERM_INVALID("조회 기간은 최대 3일까지 입니다.")
    ;

    private final String errorMessage;

    CustomErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
