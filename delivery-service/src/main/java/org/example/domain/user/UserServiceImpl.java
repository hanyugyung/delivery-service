package org.example.domain.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.exception.CustomErrorMessage;
import org.example.common.exception.InvalidParamException;
import org.example.infrastructure.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private void checkAvailableUserId(String userId) {
        userRepository.findByUserId(userId)
                .ifPresent(it -> {
                    throw new InvalidParamException(CustomErrorMessage.USER_ID_ALREADY_EXISTED);
                });
    }

    @Override
    @Transactional
    public Long signUp(UserCommand.UserSignUp command) {
        checkAvailableUserId(command.getUserId());
        return userRepository.save(command.toEntity()).getId();
    }
}