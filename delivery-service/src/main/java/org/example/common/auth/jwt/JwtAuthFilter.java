package org.example.common.auth.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.common.auth.AccessUser;
import org.example.common.auth.AuthFilter;
import org.example.common.exception.CustomErrorMessage;
import org.example.common.util.Utils;
import org.example.interfaces.CommonResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

public class JwtAuthFilter extends AuthFilter {

    private String key;

    public JwtAuthFilter(String key) {
        this.key = key;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Claims tokenBody = JwtValidator.validateAndGetClaims(key, request.getHeader("Token"));

        if (tokenBody == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            Utils.getObjectMapper().writeValue(
                    response.getOutputStream(), CommonResponse.fail(CustomErrorMessage.USER_FAIL_AUTHORIZATION));
        } else {

            TokenInfo info = Utils
                    .getObjectMapper()
                    .convertValue(tokenBody.get("info"), TokenInfo.class);

            AccessUser user = new AccessUser(info.id(), info.userName(), info.roles());

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}
