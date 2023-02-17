package org.example.domain.delivery;

import lombok.RequiredArgsConstructor;
import org.example.infrastructure.delivery.DeliveryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DeliveryInfo.GetDeliveries> getUserDeliveries(Long userId, ZonedDateTime before, ZonedDateTime now) {
        return deliveryRepository.getUserDeliveries(userId, before, now)
                .stream()
                .map(DeliveryInfo.GetDeliveries::of)
                .toList();
    }
}
