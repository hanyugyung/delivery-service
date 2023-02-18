package org.example.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

public class UserCommand {

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Builder
    @Getter
    @AllArgsConstructor
    public static class UserSignUp {
        private String userId;
        private String password;
        private String userName;

        public User toEntity() {
            /**
             * TODO 시큐어리티 관련 기능 추가
             * 패스워드 암호화, 어디서 passwordEncoder 를 주입받아서 암호화할지
             */
            return new User(userId, password, userName);
//            return new User(userId, passwordEncoder.encode(password), userName);
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
