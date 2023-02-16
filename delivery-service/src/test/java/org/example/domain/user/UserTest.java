package org.example.domain.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserTest {

    @Test
    void 테스트_User_엔티티_생성() {

        // given
        String userId = "userId" + UUID.randomUUID().toString().substring(0, 5);
        String password = "User1234!@#$";
        String userName = "userName";

        // when
        User user = new User(userId, password, userName);

        // then
        assertNotNull(user);
    }

    @Test
    void 테스트_User엔티티_생성_유효하지_않은_값_요청시() {

        // given
        String userId = "userId" + UUID.randomUUID().toString().substring(0, 5);
        String password = "";
        String userName = "";

        // when, then
        assertThrows(IllegalArgumentException.class,
                () -> new User(userId, password, userName));
    }
}
