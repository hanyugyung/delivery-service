package org.example.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserCommand {

    @Builder
    @Getter
    @AllArgsConstructor
    public static class UserSignUp {
        private String userId;
        private String password;
        private String userName;

        public User toEntity(PasswordEncoder passwordEncoder) {
            return new User(userId, passwordEncoder.encode(password), userName);
        }
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class UserLogin {
        private String userId;
        private String password;
    }
}
