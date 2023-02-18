package org.example.domain.delivery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

public class DeliveryInfo {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetDeliveries {
        private Long userId;
        private ZonedDateTime orderedAt;
        private Delivery.Status status;
        private String destination;
        private Long rider;
        private String memo;
        private List<OrderItem> orderItemList;

        // FIXME Mapstruct 로 변경하면 좋을텐데!
        public static GetDeliveries of(Delivery delivery) {
            return GetDeliveries.builder()
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
