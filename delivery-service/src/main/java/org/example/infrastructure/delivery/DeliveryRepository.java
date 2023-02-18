package org.example.infrastructure.delivery;

import org.example.domain.delivery.Delivery;
import org.example.infrastructure.delivery.querydsl.DeliveryCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long>, DeliveryCustomRepository {
}
