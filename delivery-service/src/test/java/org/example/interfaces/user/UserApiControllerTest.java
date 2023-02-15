package org.example.interfaces.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class UserApiControllerTest {

    private WebTestClient client;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        client = WebTestClient.bindToServer()
                .baseUrl(String.format("http://localhost:%d/user", port))
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
                = new ParameterizedTypeReference<>() {};

        UserApiDto.UserSignUpResponse response = client.post()
                .uri("/user/sing-up")
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
                = new ParameterizedTypeReference<>() {};

        CommonResponse response = client.post()
                .uri("/user/sing-up")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ref)
                .returnResult()
                .getResponseBody();

        // then
        assertNotNull(response);
        assertNotNull(response.getErrorMessage());
    }

    private UserApiDto.UserSignUpRequest getRequest(String userId, String password, String userName) {
        return UserApiDto.UserSignUpRequest
                .builder()
                .userId(userId)
                .password(password)
                .userName(userName)
                .build();
    }
}