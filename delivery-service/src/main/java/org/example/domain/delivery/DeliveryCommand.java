package org.example.domain.delivery;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class DeliveryCommand {

    @Builder
    @Getter
    @AllArgsConstructor
    public static class PatchDelivery {
        private String destination;
    }
}
