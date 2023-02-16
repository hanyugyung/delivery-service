package org.example.interfaces.user;

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
@RequestMapping("/user")
public class UserApiController {

    private final UserService userService;

    @PostMapping("/sing-up")
    public CommonResponse<UserApiDto.UserSignUpResponse> signup(
            @RequestBody @Valid UserApiDto.UserSignUpRequest request
    ) {
        UserApiDto.UserSignUpResponse response =
                UserApiDto.UserSignUpResponse.of(userService.signUp(request.toCommand()));
        return CommonResponse.success(response);
    }


}
