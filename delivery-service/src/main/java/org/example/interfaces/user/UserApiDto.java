package org.example.interfaces.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.domain.user.UserCommand;

public class UserApiDto {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class UserSignUpRequest {
        @NotBlank(message = "아이디는 필수 입력 값 입니다.")
        private String userId;

        @NotBlank(message = "비밀번호는 필수 입력 값 입니다.")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[$@$!%*#?&])" +
                "([A-Z0-9$@$!%*#?&]|([a-z0-9$@$!%*#?&])|([A-Za-z0-9])|([A-Za-z$@$!%*#?&])|([A-Za-z0-9$@$!%*#?&])){12}$"
                , message = "비밀번호는 영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로 12자리 이상이어야 합니다.")
        private String password;
        private String userName;

        public UserCommand.UserSignUp toCommand() {
            return UserCommand.UserSignUp.builder()
                    .userId(userId)
                    .password(password)
                    .userName(userName)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class UserSignUpResponse {
        private String id;
    }
}
