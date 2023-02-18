package org.example.infrastructure.delivery.querydsl;

import org.example.domain.delivery.Delivery;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface DeliveryCustomRepository {

    List<Delivery> getUserDeliveries(Long userId, ZonedDateTime start, ZonedDateTime end);

    List<Delivery> getByIdAndUserId(Long id, Long userId); // null 방지를 위해 list 로 반환
}
