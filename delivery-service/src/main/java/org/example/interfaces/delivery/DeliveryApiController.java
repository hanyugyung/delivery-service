package org.example.interfaces.delivery;

import lombok.RequiredArgsConstructor;
import org.example.common.exception.CustomErrorMessage;
import org.example.common.exception.InvalidParamException;
import org.example.domain.delivery.DeliveryService;
import org.example.interfaces.CommonResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deliveries")
public class DeliveryApiController {

    private final DeliveryService deliveryService;

    @GetMapping("")
    public CommonResponse<DeliveryApiDto.GetDeliveriesResponse> getList(
            @RequestParam(name = "from", required = false
            ) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from
    ) {
        // TODO 인증된 사용자의 pk 로 변경
        Long userId = 2L;

        ZonedDateTime before
                = from == null ? LocalDate.now().minusDays(2).atStartOfDay(ZoneId.systemDefault()) : from.atZone(ZoneId.systemDefault());

        if (from != null && from.isBefore(before.toLocalDateTime())) {
            throw new InvalidParamException(CustomErrorMessage.DELIVERY_SEARCH_TERM_INVALID);
        }

        DeliveryApiDto.GetDeliveriesResponse response =
                DeliveryApiDto.GetDeliveriesResponse
                        .of(deliveryService.getUserDeliveries(userId, before, ZonedDateTime.now()));
        return CommonResponse.success(response);
    }
}