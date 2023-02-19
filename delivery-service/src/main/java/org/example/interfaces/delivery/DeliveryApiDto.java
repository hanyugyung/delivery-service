package org.example.interfaces.delivery;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "배달 API DTO")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetDeliveriesResponse {
        @Schema(description = "조회결과값")
        private List<DeliveryInfo.GetDeliveries> list;

        public static GetDeliveriesResponse of(List<DeliveryInfo.GetDeliveries> list) {
            return new GetDeliveriesResponse(list);
        }
    }

    @Schema(description = "배달 API DTO")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PatchDeliveryRequest {
        @Schema(description = "변경할 수령지", example = "부산")
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

    @Schema(description = "배달 API DTO")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PatchDeliveryResponse {
        @Schema(description = "변경된 배달 pk")
        private Long id;
        @Schema(description = "변경된 배달 수령지", example = "부산")
        private String destination;

        public static PatchDeliveryResponse of(DeliveryInfo.PatchDelivery delivery) {
            return new PatchDeliveryResponse(delivery.getId(), delivery.getDestination());
        }
    }

}
