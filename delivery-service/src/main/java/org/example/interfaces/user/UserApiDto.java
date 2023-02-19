package org.example.interfaces.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.user.UserCommand;
import org.example.domain.user.UserInfo;

public class UserApiDto {

    @Schema(description = "사용자 API DTO")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserSignUpRequest {

        @Schema(description = "회원가입할 사용자 아이디")
        @NotBlank(message = "아이디는 필수 입력 값 입니다.")
        private String userId;

        @Schema(description = "회원가입할 사용자 비밀번호, 영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로 12자리 이상")
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

    @Schema(description = "사용자 API DTO")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserSignUpResponse {

        @Schema(description = "회원가입한 사용자 pk")
        private Long id;

        public static UserSignUpResponse of(UserInfo.UserSignUp info) {
            return new UserSignUpResponse(info.getId());
        }
    }

    @Schema(description = "사용자 API DTO")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserLoginRequest {

        @Schema(description = "사용자 아이디")
        @NotBlank(message = "아이디는 필수 입력 값 입니다.")
        private String userId;

        @Schema(description = "사용자 비밀번호")
        @NotBlank(message = "비밀번호는 필수 입력 값 입니다.")
        private String password;

        public UserCommand.UserLogin toCommand() {
            return UserCommand.UserLogin.builder()
                    .userId(userId)
                    .password(password)
                    .build();
        }
    }

    @Schema(description = "사용자 API DTO")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserLoginResponse {

        @Schema(description = "토큰")
        private String token;

        public static UserLoginResponse of(UserInfo.UserLogin info) {
            return new UserLoginResponse(info.getToken());
        }
    }
}
