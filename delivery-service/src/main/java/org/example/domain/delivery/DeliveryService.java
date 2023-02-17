package org.example.domain.delivery;

import java.time.ZonedDateTime;
import java.util.List;

public interface DeliveryService {
    List<DeliveryInfo.GetDeliveries> getUserDeliveries(Long id, ZonedDateTime before, ZonedDateTime now);

    DeliveryInfo.PatchDelivery patchDestination(Long id, Long userId, DeliveryCommand.PatchDelivery command);
}
