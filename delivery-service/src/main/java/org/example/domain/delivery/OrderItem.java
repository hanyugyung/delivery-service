package org.example.domain.delivery;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.Base;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderItem extends Base {

    /**
     * 주문한 상품
     */
    @NotNull
    private Long itemId;

    /**
     * 어떤 주문인지
     */
    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;

    /**
     * 수량
     */
    @NotNull
    private Integer count;

    @Builder
    public OrderItem(Long itemId, Integer count) {
        this.itemId = itemId;
        this.count = count;
    }
}
