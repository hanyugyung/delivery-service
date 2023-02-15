package org.example.interfaces.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.common.exception.CustomErrorMessage;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {

    private Result result;
    private T data;
    private String errorMessage;

    public static <T> CommonResponse<T> success(T data) {
        return (CommonResponse<T>) CommonResponse.builder()
                .result(Result.SUCCESS)
                .data(data)
                .build();
    }

    public static CommonResponse fail(CustomErrorMessage errorMessage) {
        return CommonResponse.builder()
                .result(Result.FAIL)
                .errorMessage(errorMessage.getErrorMessage())
                .build();
    }

    public static CommonResponse fail(String errorMessage) {
        return CommonResponse.builder()
                .result(Result.FAIL)
                .errorMessage(errorMessage)
                .build();
    }

    public enum Result {
        SUCCESS, FAIL
    }

}
