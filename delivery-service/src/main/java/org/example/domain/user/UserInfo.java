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

}
