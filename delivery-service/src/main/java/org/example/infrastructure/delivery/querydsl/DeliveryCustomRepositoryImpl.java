package org.example.infrastructure.delivery.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.domain.delivery.Delivery;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

import static org.example.domain.delivery.QDelivery.delivery;
import static org.example.domain.delivery.QOrder.order;

@RequiredArgsConstructor
public class DeliveryCustomRepositoryImpl implements DeliveryCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Delivery> getUserDeliveries(Long userId, ZonedDateTime start, ZonedDateTime end) {
        return queryFactory.select(delivery)
                .join(order)
                .where(order.userId.eq(userId)
                    .and(order.orderedAt.between(start, end)))
                .fetch();
    }
}
