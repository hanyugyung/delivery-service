package org.example.common.auth.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.domain.user.UserInfo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JwtGenerator {

    private static TokenInfo makeTokenInfo(Long id, String userName) {
        return new TokenInfo(id, userName, List.of("user"));
    }

    public static UserInfo.UserLogin generateToken(
            Long id
            , String userName
            , JwtConfigProperty jwtProperty) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("info", makeTokenInfo(id, userName)); // 토큰 내 사용자 정보

        Date iat = new Date();
        Date exp = new Date(iat.getTime() + jwtProperty.getExpiredTime());

        return UserInfo.UserLogin.of(Jwts.builder()
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")
                .setIssuer(jwtProperty.getIssuer())
                .setIssuedAt(iat)
                .setExpiration(exp)
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, jwtProperty.getSecretKey())
                .compact());
    }
}
