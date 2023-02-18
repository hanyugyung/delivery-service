package org.example.domain.delivery;

import org.example.infrastructure.delivery.DeliveryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class DeliveryServiceTest {

    private int count;

    private Long userId = 1L;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private DeliveryService deliveryService;

    @BeforeEach
    void setUp() {

        saveDelivery();
        saveDelivery();
        saveDelivery();

        count = 3;
    }

    @Test
    void 사용자의_주문_배달_목록_조회() {

        // given
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime before = ZonedDateTime.now().minusDays(3);
        Long userId = 1l;

        // when
        List<DeliveryInfo.GetDeliveries> deliveries = deliveryService.getUserDeliveries(userId, before, now);

        // then
        assertEquals(count, deliveries.size());
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
