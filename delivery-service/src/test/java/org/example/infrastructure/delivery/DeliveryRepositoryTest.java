package org.example.infrastructure.delivery;

import org.example.domain.delivery.Delivery;
import org.example.domain.delivery.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class DeliveryRepositoryTest {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Test
    void 테스트_배달목록_조회() {

        Long userId = 1L;
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime before = LocalDate.now().minusDays(2).atStartOfDay(ZoneId.systemDefault());
        Order order = new Order(userId, ZonedDateTime.now().minusDays(1));

        Delivery delivery = Delivery.builder()
                .order(order)
                .destination("서울")
                .memo("문앞에 두고 가주세요").build();

        deliveryRepository.save(delivery);

        List<Delivery> deliveryList = deliveryRepository.getUserDeliveries(userId, before, now);
        assertEquals(1, deliveryList.size());
    }
}