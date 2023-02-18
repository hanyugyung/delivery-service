package org.example.infrastructure.delivery.querydsl;

import org.example.domain.delivery.Delivery;

import java.time.ZonedDateTime;
import java.util.List;

public interface DeliveryCustomRepository {

    List<Delivery> getUserDeliveries(Long userId, ZonedDateTime start, ZonedDateTime end);

}
