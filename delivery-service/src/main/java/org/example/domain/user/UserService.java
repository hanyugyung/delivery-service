package org.example.domain.user;

public interface UserService {

    Long signUp(UserCommand.UserSignUp command);

}
