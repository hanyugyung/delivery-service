package org.example.infrastructure.delivery.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.domain.delivery.Delivery;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.example.domain.delivery.QDelivery.delivery;
import static org.example.domain.delivery.QOrder.order;
import static org.example.domain.delivery.QOrderItem.orderItem;

@RequiredArgsConstructor
public class DeliveryCustomRepositoryImpl implements DeliveryCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Delivery> getUserDeliveries(Long userId, ZonedDateTime start, ZonedDateTime end) {
        return queryFactory.select(delivery)
                .distinct()
                .from(delivery)
                .leftJoin(delivery.order, order)
                .where(order.userId.eq(userId)
                        .and(order.orderedAt.between(start, end)))
                .fetchJoin()
                .leftJoin(order.orderItemList, orderItem)
                .fetchJoin()
                .fetch();
    }

    @Override
    public List<Delivery> getByIdAndUserId(Long id, Long userId) {
        return queryFactory.select(delivery)
                .join(order)
                .where(delivery.id.eq(id)
                        .and(delivery.order.userId.eq(userId)))
                .orderBy(delivery.createdAt.desc())
                .fetch();
    }
}
