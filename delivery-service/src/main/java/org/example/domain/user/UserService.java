package org.example.domain.user;

public interface UserService {

    UserInfo.UserSignUp signUp(UserCommand.UserSignUp command);

    UserInfo.UserLogin login(UserCommand.UserLogin commandLogin);
}
