package org.example.interfaces.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.domain.user.UserService;
import org.example.interfaces.CommonResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "회원가입 api 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping("/sing-up")
    public CommonResponse<UserApiDto.UserSignUpResponse> signup(
            @RequestBody @Valid UserApiDto.UserSignUpRequest request
    ) {
        UserApiDto.UserSignUpResponse response =
                UserApiDto.UserSignUpResponse.of(userService.signUp(request.toCommand()));
        return CommonResponse.success(response);
    }

    @Operation(summary = "토큰 발급 요청", description = "토큰 발급 요청 api 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping("/login")
    public CommonResponse<UserApiDto.UserLoginResponse> login(
            @RequestBody @Valid UserApiDto.UserLoginRequest request
    ) {
        UserApiDto.UserLoginResponse response =
                UserApiDto.UserLoginResponse.of(userService.login(request.toCommand()));
        return CommonResponse.success(response);
    }
}
