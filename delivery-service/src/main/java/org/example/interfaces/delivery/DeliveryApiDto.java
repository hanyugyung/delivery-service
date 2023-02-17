package org.example.interfaces.delivery;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.delivery.DeliveryCommand;
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

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PatchDeliveryRequest {
        @NotBlank
        @Size(max=200)
        private String destination;
        public DeliveryCommand.PatchDelivery toCommand() {
            return DeliveryCommand.PatchDelivery
                    .builder()
                    .destination(this.destination)
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PatchDeliveryResponse {
        private Long id;
        private String destination;

        public static PatchDeliveryResponse of(DeliveryInfo.PatchDelivery delivery) {
            return new PatchDeliveryResponse(delivery.getId(), delivery.getDestination());
        }
    }

}
