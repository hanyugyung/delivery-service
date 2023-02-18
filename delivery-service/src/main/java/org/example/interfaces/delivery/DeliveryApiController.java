package org.example.interfaces.delivery;

import lombok.RequiredArgsConstructor;
import org.example.common.auth.AccessUser;
import org.example.common.exception.CustomErrorMessage;
import org.example.common.exception.InvalidParamException;
import org.example.domain.delivery.DeliveryService;
import org.example.interfaces.CommonResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            , @AuthenticationPrincipal AccessUser accessUser
    ) {

        ZonedDateTime limit = LocalDate.now().minusDays(2).atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime before;

        if(from == null) before = limit;
        else {
            before = from.atZone(ZoneId.systemDefault());
            if(from.isBefore(limit.toLocalDateTime())) {
                throw new InvalidParamException(CustomErrorMessage.DELIVERY_SEARCH_TERM_INVALID);
            }
        }

        DeliveryApiDto.GetDeliveriesResponse response =
                DeliveryApiDto.GetDeliveriesResponse
                        .of(deliveryService.getUserDeliveries(accessUser.getId(), before, ZonedDateTime.now()));
        return CommonResponse.success(response);
    }
}
