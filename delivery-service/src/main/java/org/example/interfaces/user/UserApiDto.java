package org.example.interfaces.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.user.UserCommand;
import org.example.domain.user.UserInfo;

public class UserApiDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserSignUpRequest {
        @NotBlank(message = "아이디는 필수 입력 값 입니다.")
        private String userId;

        @NotBlank(message = "비밀번호는 필수 입력 값 입니다.")
        @Pattern(regexp = "^(?:(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])" +
                "|(?=.*[A-Z])(?=.*[a-z])(?=.*[$@$!%*#?&])" +
                "|(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*#?&])" +
                "|(?=.*[a-z])(?=.*[0-9])(?=.*[$@$!%*#?&]))" +
                "[A-Za-z0-9$@$!%*#?&]{12,}$"
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
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserSignUpResponse {
        private Long id;

        public static UserSignUpResponse of(UserInfo.UserSignUp info) {
            return new UserSignUpResponse(info.getId());
        }
    }
}
