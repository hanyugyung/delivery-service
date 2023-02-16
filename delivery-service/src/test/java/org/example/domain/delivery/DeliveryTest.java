package org.example.domain.delivery;

import org.example.common.exception.InvalidParamException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class DeliveryTest {

    @Test
    void 라이더배정이후_배송지_변경시_오류() {

        // given
        Delivery delivery = Delivery.builder()
                .order(new Order(1L, ZonedDateTime.now()))
                .destination("서울")
                .memo("문앞에 두고 가주세요").build();

        delivery.assignRider(1L);

        // when, then
        assertThrows(InvalidParamException.class
                , () -> delivery.changeDestination("부산"));
    }

    @Test
    void 배송시작이후_배송지_변경시_오류() {

        // given
        Delivery delivery = Delivery.builder()
                .order(new Order(1L, ZonedDateTime.now()))
                .destination("서울")
                .memo("문앞에 두고 가주세요").build();

        delivery.startDelivery();

        // when, then
        assertThrows(InvalidParamException.class
                , () -> delivery.changeDestination("부산"));
    }

    @Test
    void 배송완료이후_배송지_변경시_오류() {

        // given
        Delivery delivery = Delivery.builder()
                .order(new Order(1L, ZonedDateTime.now()))
                .destination("서울")
                .memo("문앞에 두고 가주세요").build();

        delivery.completeDelivery();

        // when, then
        assertThrows(InvalidParamException.class
                , () -> delivery.changeDestination("부산"));
    }
}
