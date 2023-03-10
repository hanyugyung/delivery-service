package org.example.interfaces.user;

import org.example.domain.user.UserCommand;
import org.example.domain.user.UserService;
import org.example.interfaces.CommonResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class UserApiControllerTest {

    private WebTestClient client;

    @LocalServerPort
    private int port;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        client = WebTestClient.bindToServer()
                .baseUrl(String.format("http://localhost:%d/api/users", port))
                .build();
    }

    @Test
    void 테스트_사용자_회원가입_API_요청() {

        // given
        String userId = "userId" + UUID.randomUUID().toString().substring(0, 5);
        String password = "User1234!@#$";
        String userName = "userName";

        UserApiDto.UserSignUpRequest request = getRequest(userId, password, userName);

        // when
        ParameterizedTypeReference<CommonResponse<UserApiDto.UserSignUpResponse>> ref
                = new ParameterizedTypeReference<>() {
        };

        UserApiDto.UserSignUpResponse response = client.post()
                .uri("/sing-up")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(ref)
                .returnResult()
                .getResponseBody()
                .getData();


        // then
        assertNotNull(response);
        assertNotNull(response.getId());
    }

    @Test
    void 테스트_사용자_회원가입_API_요청_비밀번호_패턴_불일치() {

        // given
        String userId = "userId" + UUID.randomUUID().toString().substring(0, 5);
        String password = "1234!@#$";
        String userName = "userName";

        UserApiDto.UserSignUpRequest request = getRequest(userId, password, userName);

        // when, then
        ParameterizedTypeReference<CommonResponse<UserApiDto.UserSignUpResponse>> ref
                = new ParameterizedTypeReference<>() {
        };

        CommonResponse response = client.post()
                .uri("/sing-up")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ref)
                .returnResult()
                .getResponseBody();

        // then
        assertNotNull(response);
        assertEquals(CommonResponse.Result.FAIL, response.getResult());
    }

    @Test
    void 테스트_사용자_로그인() {

        // given
        String userId = "userId" + UUID.randomUUID().toString().substring(0, 5);
        String password = "User1234!@#$";
        String userName = "userName";
        userService.signUp(
                new UserCommand.UserSignUp(userId, password, userName));
        UserApiDto.UserLoginRequest request = getRequest(userId, password);

        // when
        ParameterizedTypeReference<CommonResponse<UserApiDto.UserLoginResponse>> ref
                = new ParameterizedTypeReference<>() {
        };

        UserApiDto.UserLoginResponse response = client.post()
                .uri("/login")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(ref)
                .returnResult()
                .getResponseBody()
                .getData();

        assertNotNull(response.getToken());
    }

    @Test
    void 테스트_사용자_로그인_필수값_누락시_오류() {

        // given
        String userId = "userId" + UUID.randomUUID().toString().substring(0, 5);
        String password = "User1234!@#$";
        String userName = "userName";
        userService.signUp(
                new UserCommand.UserSignUp(userId, password, userName));
        UserApiDto.UserLoginRequest request = getRequest(userId, "");

        // when
        ParameterizedTypeReference<CommonResponse<UserApiDto.UserLoginResponse>> ref
                = new ParameterizedTypeReference<>() {
        };

        CommonResponse response = client.post()
                .uri("/login")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ref)
                .returnResult()
                .getResponseBody();

        assertEquals(CommonResponse.Result.FAIL, response.getResult());
    }

    @Test
    void 테스트_비밀번호_불일치시_오류() {

        // given
        String userId = "userId" + UUID.randomUUID().toString().substring(0, 5);
        String password = "User1234!@#$";
        String userName = "userName";
        userService.signUp(
                new UserCommand.UserSignUp(userId, password, userName));
        UserApiDto.UserLoginRequest request = getRequest(userId, "User111111!!!");

        // when
        ParameterizedTypeReference<CommonResponse<UserApiDto.UserLoginResponse>> ref
                = new ParameterizedTypeReference<>() {
        };

        CommonResponse response = client.post()
                .uri("/login")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ref)
                .returnResult()
                .getResponseBody();

        assertEquals(CommonResponse.Result.FAIL, response.getResult());
    }

    private UserApiDto.UserSignUpRequest getRequest(String userId, String password, String userName) {
        return UserApiDto.UserSignUpRequest
                .builder()
                .userId(userId)
                .password(password)
                .userName(userName)
                .build();
    }

    private UserApiDto.UserLoginRequest getRequest(String userId, String password) {
        return UserApiDto.UserLoginRequest
                .builder()
                .userId(userId)
                .password(password)
                .build();
    }
}