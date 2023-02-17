package org.example.interfaces.delivery;

import org.example.domain.delivery.Delivery;
import org.example.domain.delivery.Order;
import org.example.infrastructure.delivery.DeliveryRepository;
import org.example.interfaces.CommonResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class DeliveryApiControllerTest {

    private Long userId = 2L;

    private WebTestClient client;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        client = WebTestClient.bindToServer()
                .responseTimeout(Duration.ofSeconds(60))
                .baseUrl(String.format("http://localhost:%d/deliveries", port))
                .build();
    }

    @Test
    void 테스트_사용자의_주문_배달_목록_조회_API_요청() {

        // given
        saveDelivery();

        ZonedDateTime from = ZonedDateTime.now().minusDays(2);

        // when
        ParameterizedTypeReference<CommonResponse<DeliveryApiDto.GetDeliveriesResponse>> ref
                = new ParameterizedTypeReference<>() {};

        DeliveryApiDto.GetDeliveriesResponse response = client.get()
                .uri(String.format("?from=%s", from.toLocalDateTime()))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(ref)
                .returnResult()
                .getResponseBody()
                .getData();


        // then
        assertNotNull(response);
        assertEquals(1, response.getList().size());
    }

    @Test
    void 테스트_사용자의_주문_배달_목록시_기간3일_초과() {

        // given
        saveDelivery();

        ZonedDateTime from = ZonedDateTime.now().minusDays(3);

        // when
        ParameterizedTypeReference<CommonResponse<DeliveryApiDto.GetDeliveriesResponse>> ref
                = new ParameterizedTypeReference<>() {};

        CommonResponse response = client.get()
                .uri(String.format("?from=%s", from.toLocalDateTime()))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(ref)
                .returnResult()
                .getResponseBody();


        // then
        assertNotNull(response);
        assertEquals(CommonResponse.Result.FAIL, response.getResult());
    }

    private void saveDelivery() {

        Order order = new Order(userId, ZonedDateTime.now().minusDays(1));

        Delivery delivery = Delivery.builder()
                .order(order)
                .destination("서울")
                .memo("문앞에 두고 가주세요").build();

        deliveryRepository.save(delivery);
    }
}
