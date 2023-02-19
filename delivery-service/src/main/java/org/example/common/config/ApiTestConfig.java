package org.example.common.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.domain.delivery.Delivery;
import org.example.domain.delivery.Order;
import org.example.infrastructure.delivery.DeliveryRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
@Profile("default")
@RequiredArgsConstructor
public class ApiTestConfig {

    private final DeliveryRepository deliveryRepository;

    @PostConstruct //FIXME 배달하기 API 가 없기 때문에 확인하기? 용으로만 쓸것!!
    private void prepareTest() {
        // 테스트코드와 데이터가 겹치지 않게 하기 위함
        // 배달지 수정 가능
        Order order = new Order(1L, ZonedDateTime.now().minusDays(1));

        Delivery delivery = Delivery.builder()
                .order(order)
                .destination("서울")
                .memo("문앞에 두고 가주세요").build();

        deliveryRepository.save(delivery);

        // 배달지 수정 불가능
        Order order2 = new Order(1L, ZonedDateTime.now().minusDays(1));

        Delivery delivery2 = Delivery.builder()
                .order(order2)
                .destination("서울")
                .memo("문앞에 두고 가주세요").build();
        delivery2.assignRider(5L);
        deliveryRepository.save(delivery2);
    }
}
