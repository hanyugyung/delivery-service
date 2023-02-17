package org.example.domain.delivery;

import lombok.RequiredArgsConstructor;
import org.example.common.exception.CustomErrorMessage;
import org.example.common.exception.InvalidParamException;
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

    @Override
    @Transactional
    public DeliveryInfo.PatchDelivery patchDestination(Long id, Long userId, DeliveryCommand.PatchDelivery command) {

        List<Delivery> result = deliveryRepository.getByIdAndUserId(id, userId);
        if (result.isEmpty()) throw new InvalidParamException(CustomErrorMessage.DELIVERY_SEARCH_NO_INVALID);

        Delivery one = result.get(0);
        one.changeDestination(command.getDestination());
        return DeliveryInfo.PatchDelivery.of(deliveryRepository.save(one));
    }
}
