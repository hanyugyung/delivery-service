package org.example.domain.delivery;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.Base;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Order extends Base {

    /**
     * 주문자
     */
    @NotNull
    private Long userId;

    @OneToOne(mappedBy = "order")
    private Delivery delivery;

    /**
     * 주문 상품 목록
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.PERSIST)
    private final List<OrderItem> orderItemList
            = List.of(new OrderItem(1L, 1), new OrderItem(2L, 1));

    /**
     * 주문 일시
     */
    @NotNull
    private ZonedDateTime orderedAt;

    @Builder
    public Order(Long userId, ZonedDateTime orderedAt) {
        this.userId = userId;
        this.orderedAt = orderedAt;
    }
}
