package org.example.infrastructure.delivery.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.domain.delivery.Delivery;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

import static org.example.domain.delivery.QDelivery.delivery;
import static org.example.domain.delivery.QOrder.order;
import static org.example.domain.delivery.QOrderItem.orderItem;

@RequiredArgsConstructor
public class DeliveryCustomRepositoryImpl implements DeliveryCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Delivery> getUserDeliveries(Long userId, ZonedDateTime start, ZonedDateTime end) {
        List<Delivery> result = queryFactory.select(delivery)
                .distinct()
                .from(delivery)
                .leftJoin(delivery.order, order)
                .where(order.userId.eq(userId)
                        .and(order.orderedAt.between(start, end)))
                .fetchJoin()
                .leftJoin(order.orderItemList, orderItem)
                .fetchJoin()
                .fetch();
        return result;
    }
}
