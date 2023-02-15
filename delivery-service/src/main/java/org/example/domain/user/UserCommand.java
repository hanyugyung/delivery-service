package org.example.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class UserCommand {

    @Builder
    @Getter
    @AllArgsConstructor
    public static class UserSignUp {
        private String userId;
        private String password;
        private String userName;

        public User toEntity(String encryptedPassword) {
            return new User(userId, encryptedPassword, userName);
        }
    }
}
