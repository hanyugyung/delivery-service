package org.example.domain.user;

import org.example.common.exception.InvalidParamException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void 테스트_사용자_회원가입() {

        // given
        String userId = "userId" + UUID.randomUUID().toString().substring(0, 5);
        String password = "User1234!@#$";
        String userName = "userName";
        UserCommand.UserSignUp command
                = new UserCommand.UserSignUp(userId, password, userName);

        // when
        Long user = userService.signUp(command);

        // then
        assertNotNull(user);
    }

    @Test
    void 테스트_사용자_회원가입_id중복_오류() {

        // given
        String userId = "userId";
        String password = "User1234!@#$";
        String userName = "userName";
        UserCommand.UserSignUp command
                = new UserCommand.UserSignUp(userId, password, userName);

        Long user = userService.signUp(command);

        assertNotNull(user);

        // when, then
        assertThrows(InvalidParamException.class,
                () -> userService.signUp(command));
    }


}