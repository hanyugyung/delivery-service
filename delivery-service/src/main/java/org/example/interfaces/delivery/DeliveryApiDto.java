package org.example.interfaces.delivery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.delivery.DeliveryInfo;

import java.util.List;

public class DeliveryApiDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetDeliveriesResponse {
        private List<DeliveryInfo.GetDeliveries> list;

        public static GetDeliveriesResponse of(List<DeliveryInfo.GetDeliveries> list) {
            return new GetDeliveriesResponse(list);
        }
    }
}
