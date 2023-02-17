package org.example.domain.delivery;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.common.exception.CustomErrorMessage;
import org.example.common.exception.InvalidParamException;
import org.example.domain.Base;

@Entity
@Table(name = "deliveries")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Delivery extends Base {

    /**
     * 어떤 주문에 대한 배달인지?
     */
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "orderId")
    private Order order;

    /**
     * 주문 상태
     */
    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * 수령지
     */
    @NotNull
    private String destination;

    /**
     * 담당 배달 기사
     */
    private Long rider;

    /**
     * 배송 메모
     */
    @Size(max = 1234567)
    private String memo;

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        DELIVERY_PREPARE("준비"),
        ASSIGN_RIDER("배달 기사 배정"),
        IN_DELIVERY("배달 시작"),
        DELIVERY_COMPLETE("배달 완료");

        private final String description;
    }

    @Builder
    public Delivery(Order order, String destination, String memo) {
        this.order = order;
        this.status = Status.DELIVERY_PREPARE;
        this.destination = destination;
        this.memo = memo;
    }

    public void assignRider(Long rider) {
        if (this.status != Status.DELIVERY_PREPARE) {
            throw new InvalidParamException(CustomErrorMessage.DELIVERY_HAS_ALREADY_STARTED);
        }
        this.rider = rider;
        this.status = Status.ASSIGN_RIDER;
    }

    public void startDelivery() {
        if (this.status == Status.DELIVERY_COMPLETE) {
            throw new InvalidParamException(CustomErrorMessage.DELIVERY_STATUS_INVALID);
        }
        this.status = Status.IN_DELIVERY;
    }

    public void completeDelivery() {
        this.status = Status.IN_DELIVERY;
    }

    public void changeDestination(String destination) {
        if (this.status != Status.DELIVERY_PREPARE) {
            throw new InvalidParamException(CustomErrorMessage.DELIVERY_HAS_ALREADY_STARTED);
        }
        this.destination = destination;
    }
}
