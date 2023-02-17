package org.example.interfaces.delivery;

import org.example.domain.delivery.Delivery;
import org.example.domain.delivery.Order;
import org.example.infrastructure.delivery.DeliveryRepository;
import org.example.interfaces.CommonResponse;
import org.example.interfaces.user.UserApiDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

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

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime from = ZonedDateTime.now().minusDays(3);

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
        assertTrue(response.getList().size() > 0);
    }

    @Test
    void 테스트_배달수령지_수정_API_요청() {

        // given
        Delivery delivery = saveDelivery();

        String destination = "부산";
        DeliveryApiDto.PatchDeliveryRequest request = getRequest(destination);

        // when
        ParameterizedTypeReference<CommonResponse<DeliveryApiDto.PatchDeliveryResponse>> ref
                = new ParameterizedTypeReference<>() {};

        DeliveryApiDto.PatchDeliveryResponse response = client.patch()
                .uri(String.format("/%d", delivery.getId()))
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
        Delivery delivery = saveDelivery();
        delivery.assignRider(5L);

        String destination = "부산";
        DeliveryApiDto.PatchDeliveryRequest request = getRequest(destination);

        // when
        ParameterizedTypeReference<CommonResponse<DeliveryApiDto.PatchDeliveryResponse>> ref
                = new ParameterizedTypeReference<>() {};

        DeliveryApiDto.PatchDeliveryResponse response = client.patch()
                .uri(String.format("/%d", delivery.getId()))
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

    private Delivery saveDelivery() {

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
}
