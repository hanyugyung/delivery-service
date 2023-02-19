package org.example.domain.delivery;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;
import java.util.List;

public class DeliveryInfo {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetDeliveries {
        private Long id;
        private Long userId;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        private ZonedDateTime orderedAt;
        private Delivery.Status status;
        private String destination;
        private Long rider;
        private String memo;
        private List<OrderItem> orderItemList;

        // FIXME Mapstruct 로 변경하면 좋을텐데!
        public static GetDeliveries of(Delivery delivery) {
            return GetDeliveries.builder()
                    .id(delivery.getId())
                    .userId(delivery.getOrder().getUserId())
                    .orderedAt(delivery.getOrder().getOrderedAt())
                    .status(delivery.getStatus())
                    .destination(delivery.getDestination())
                    .rider(delivery.getRider())
                    .memo(delivery.getMemo())
                    .orderItemList(
                            delivery.getOrder().getOrderItemList().stream().map(OrderItem::of).toList()
                    )
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItem {
        private Long itemId;
        private Integer count;

        public static OrderItem of(org.example.domain.delivery.OrderItem orderItem) {
            return new OrderItem(orderItem.getItemId(), orderItem.getCount());
        }
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class PatchDelivery {
        private Long id;
        private String destination;

        public static PatchDelivery of(Delivery delivery) {
            return new PatchDelivery(delivery.getId(), delivery.getDestination());
        }
    }


}
