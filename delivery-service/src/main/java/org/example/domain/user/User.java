package org.example.domain.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String encryptedPassword;

    private String userName;

    @Builder
    public User(String userId, String encryptedPassword, String userName) {

        if(!StringUtils.hasText(userId)) throw new IllegalArgumentException("User.userId");
        if(!StringUtils.hasText(encryptedPassword)) throw new IllegalArgumentException("User.password");
        if(!StringUtils.hasText(userName)) throw new IllegalArgumentException("User.userName");

        this.userId = userId;
        this.encryptedPassword = encryptedPassword;
        this.userName = userName;
    }
}
