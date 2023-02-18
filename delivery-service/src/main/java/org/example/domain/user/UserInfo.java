package org.example.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserInfo {

    @Getter
    @AllArgsConstructor
    public static class UserSignUp {
        private Long id;

        public static UserInfo.UserSignUp of(Long id) {
            return new UserInfo.UserSignUp(id);
        }
    }

    @Getter
    @AllArgsConstructor
    public static class UserLogin {
        private String token;
        public static UserInfo.UserLogin of(String token) {
            return new UserInfo.UserLogin(token);
        }
    }
}
