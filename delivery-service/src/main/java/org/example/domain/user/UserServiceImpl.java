package org.example.domain.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.auth.jwt.JwtConfigProperty;
import org.example.common.auth.jwt.JwtGenerator;
import org.example.common.exception.CustomErrorMessage;
import org.example.common.exception.InvalidParamException;
import org.example.infrastructure.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final JwtConfigProperty jwtConfigProperty;

    private void checkAvailableUserId(String userId) {
        userRepository.findByUserId(userId)
                .ifPresent(it -> {
                    throw new InvalidParamException(CustomErrorMessage.USER_ID_ALREADY_EXISTED);
                });
    }

    private User validateUserAndGet(String userId, String password) {

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new InvalidParamException(CustomErrorMessage.USER_FAIL_LOGIN));

        if (!passwordEncoder.matches(password, user.getEncryptedPassword())) {
            throw new InvalidParamException(CustomErrorMessage.USER_FAIL_LOGIN);
        }

        return user;
    }

    @Override
    @Transactional
    public UserInfo.UserSignUp signUp(UserCommand.UserSignUp command) {
        checkAvailableUserId(command.getUserId());
        return UserInfo.UserSignUp
                .of(userRepository.save(command.toEntity(passwordEncoder)).getId());
    }

    @Override
    public UserInfo.UserLogin login(UserCommand.UserLogin commandLogin) {
        User user = validateUserAndGet(commandLogin.getUserId(), commandLogin.getPassword());

        // FIXME role 에 대한 기능 추가, 일단 논외
        return JwtGenerator.generateToken(user.getId(), user.getUserName(), jwtConfigProperty);
    }
}
