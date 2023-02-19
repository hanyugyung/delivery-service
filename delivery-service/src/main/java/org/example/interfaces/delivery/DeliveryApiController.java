package org.example.interfaces.delivery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.example.common.auth.AccessUser;
import org.example.common.exception.CustomErrorMessage;
import org.example.common.exception.InvalidParamException;
import org.example.domain.delivery.DeliveryInfo;
import org.example.domain.delivery.DeliveryService;
import org.example.interfaces.CommonResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deliveries")
public class DeliveryApiController {

    private final DeliveryService deliveryService;

    @Operation(summary = "나의 배달 조회", description = "나의 배달 목록 조회 api 입니다.")
    @SecurityRequirement(name = "Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "403", description = "접근 거부", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @GetMapping("")
    public CommonResponse<DeliveryApiDto.GetDeliveriesResponse> getList(
            @Parameter(hidden = true) @RequestHeader(name = "Token") @NotBlank String token
            , @RequestParam(name = "from", required = false)
            @Parameter(description = "조회시작일, 기본값은 '오늘' 포함 3일", in = ParameterIn.QUERY, example = "2023-02-17T19:46:32")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from
            , @AuthenticationPrincipal AccessUser accessUser
    ) {

        ZonedDateTime limit = LocalDate.now().minusDays(2).atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime before;

        if (from == null) before = limit;
        else {
            before = from.atZone(ZoneId.systemDefault());
            if (from.isBefore(limit.toLocalDateTime())) {
                throw new InvalidParamException(CustomErrorMessage.DELIVERY_SEARCH_TERM_INVALID);
            }
        }

        DeliveryApiDto.GetDeliveriesResponse response =
                DeliveryApiDto.GetDeliveriesResponse
                        .of(deliveryService.getUserDeliveries(accessUser.getId(), before, ZonedDateTime.now()));
        return CommonResponse.success(response);
    }

    @Operation(summary = "배달 수령지 수정", description = "배달 수령지 수정 api 입니다.")
    @SecurityRequirement(name = "Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공", content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "403", description = "접근 거부", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PatchMapping("/{id}")
    public CommonResponse<DeliveryInfo.PatchDelivery> patchDestination(
            @Parameter(hidden = true) @RequestHeader(name = "Token") @NotBlank String token
            , @Parameter(description = "수정하려는 배달 pk", in = ParameterIn.PATH) @PathVariable(name = "id") Long id
            , @RequestBody @Valid DeliveryApiDto.PatchDeliveryRequest request
            , @AuthenticationPrincipal AccessUser accessUser
    ) {
        return CommonResponse.success(
                deliveryService.patchDestination(id, accessUser.getId(), request.toCommand())
        );
    }
}
