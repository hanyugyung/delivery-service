package org.example.common.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtValidator {

    public static Claims validateAndGetClaims(String key, String token) {

        if(token == null) return null;

        try {
            return Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception exception) {
            log.error("token parsing error!! e.message = {}", exception.getMessage());
        }
        return null;
    }


}
