package org.example.interfaces.delivery;

import org.example.domain.delivery.Delivery;
import org.example.domain.delivery.Order;
import org.example.domain.user.UserCommand;
import org.example.domain.user.UserInfo;
import org.example.domain.user.UserService;
import org.example.infrastructure.delivery.DeliveryRepository;
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

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class DeliveryApiControllerTest {

    private static final String TOKEN_HEADER = "Token";

    private WebTestClient client;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private UserService userService;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        client = WebTestClient.bindToServer()
                .responseTimeout(Duration.ofSeconds(60))
                .baseUrl(String.format("http://localhost:%d/api/deliveries", port))
                .build();
    }

    @Test
    void 테스트_사용자의_주문_배달_목록_조회_API_요청() {

        // given
        String userId = "delivery-user" + UUID.randomUUID().toString().substring(0, 5);
        String password = "delivery1234!@#$";
        UserInfo.UserSignUp user = getUser(userId, password);

        saveDelivery(user.getId());

        String token = getToken(userId, password);

        ZonedDateTime from = ZonedDateTime.now().minusDays(2);

        // when
        ParameterizedTypeReference<CommonResponse<DeliveryApiDto.GetDeliveriesResponse>> ref
                = new ParameterizedTypeReference<>() {
        };

        DeliveryApiDto.GetDeliveriesResponse response = client.get()
                .uri(String.format("?from=%s", from.toLocalDateTime()))
                .header(TOKEN_HEADER, token)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(ref)
                .returnResult()
                .getResponseBody()
                .getData();


        // then
        assertNotNull(response);
        assertTrue(response.getList().size() > 0);
    }

    @Test
    void 테스트_사용자의_주문_배달_목록시_기간3일_초과() {

        // given
        String userId = "delivery-user" + UUID.randomUUID().toString().substring(0, 5);
        String password = "delivery1234!@#$";
        UserInfo.UserSignUp user = getUser(userId, password);

        saveDelivery(user.getId());

        String token = getToken(userId, password);

        ZonedDateTime from = ZonedDateTime.now().minusDays(3);

        // when
        ParameterizedTypeReference<CommonResponse<DeliveryApiDto.GetDeliveriesResponse>> ref
                = new ParameterizedTypeReference<>() {
        };

        CommonResponse response = client.get()
                .uri(String.format("?from=%s", from.toLocalDateTime()))
                .header(TOKEN_HEADER, token)
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
    void 테스트_배달수령지_수정_API_요청() {

        // given
        String userId = "delivery-user" + UUID.randomUUID().toString().substring(0, 5);
        String password = "delivery1234!@#$";
        UserInfo.UserSignUp user = getUser(userId, password);

        Delivery delivery = saveDelivery(user.getId());

        String token = getToken(userId, password);


        String destination = "부산";
        DeliveryApiDto.PatchDeliveryRequest request = getRequest(destination);

        // when
        ParameterizedTypeReference<CommonResponse<DeliveryApiDto.PatchDeliveryResponse>> ref
                = new ParameterizedTypeReference<>() {
        };

        DeliveryApiDto.PatchDeliveryResponse response = client.patch()
                .uri(String.format("/%d", delivery.getId()))
                .header(TOKEN_HEADER, token)
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
        assertEquals(destination, response.getDestination());
    }

    @Test
    void 테스트_담당기사배정된_배달수령지_수정_API_요청시오류() {

        // given
        String userId = "delivery-user" + UUID.randomUUID().toString().substring(0, 5);
        String password = "delivery1234!@#$";
        UserInfo.UserSignUp user = getUser(userId, password);

        Delivery delivery = saveDelivery(user.getId());

        String token = getToken(userId, password);
        delivery.assignRider(5L);

        String destination = "부산";
        DeliveryApiDto.PatchDeliveryRequest request = getRequest(destination);

        // when
        ParameterizedTypeReference<CommonResponse<DeliveryApiDto.PatchDeliveryResponse>> ref
                = new ParameterizedTypeReference<>() {
        };

        DeliveryApiDto.PatchDeliveryResponse response = client.patch()
                .uri(String.format("/%d", delivery.getId()))
                .header(TOKEN_HEADER, token)
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
        assertEquals(destination, response.getDestination());
    }

    @Test
    void 테스트_사용자의_주문_배달_목록_조회_API_요청시_토큰만료_오류() {

        // given
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NzY4MDg0MzgyMDIsImlhdCI6MTY3NjgwODI1ODIwMiwiaXNzdWVyIjoiaGFuIiwiaW5mbyI6eyJpZCI6MSwidXNlck5hbWUiOiJzdHJpbmciLCJyb2xlcyI6WyJ1c2VyIl19fQ.yXfRezLjGKpzFUkQ-NuW9Y0Y5m6gcmfTeO1kWSUwUlw";

        // when
        ParameterizedTypeReference<CommonResponse<DeliveryApiDto.GetDeliveriesResponse>> ref
                = new ParameterizedTypeReference<>() {
        };

        CommonResponse response = client.get()
                .uri("")
                .header(TOKEN_HEADER, token)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ref)
                .returnResult()
                .getResponseBody();

        // then
        assertNotNull(response);
    }

    private Delivery saveDelivery(Long userId) {

        Order order = new Order(userId, ZonedDateTime.now().minusDays(1));

        Delivery delivery = Delivery.builder()
                .order(order)
                .destination("서울")
                .memo("문앞에 두고 가주세요").build();

        return deliveryRepository.save(delivery);
    }

    private DeliveryApiDto.PatchDeliveryRequest getRequest(String destination) {
        return DeliveryApiDto.PatchDeliveryRequest
                .builder()
                .destination(destination)
                .build();
    }

    private UserInfo.UserSignUp getUser(String userId, String password) {
        String userName = "userName";
        return userService.signUp(
                new UserCommand.UserSignUp(userId, password, userName));
    }

    private String getToken(String userId, String password) {
        // when
        UserCommand.UserLogin commandLogin
                = new UserCommand.UserLogin(userId, password);
        return userService.login(commandLogin).getToken();
    }
}
