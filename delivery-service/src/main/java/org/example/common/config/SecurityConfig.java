package org.example.common.config;

import org.example.common.auth.AuthFilter;
import org.example.common.auth.jwt.JwtAuthFilter;
import org.example.common.exception.CustomErrorMessage;
import org.example.common.util.Utils;
import org.example.interfaces.CommonResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${jwt.secret-key}")
    private String key;

    public AuthFilter authFilter() {
        return new JwtAuthFilter(key); // 이 필터를 빈으로 주입하면, ignore 처리가 안된다
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().requestMatchers(
                "*/api-docs/**",
                "/swagger-ui/**",
                "/user/**"
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests()
                .requestMatchers("/delivery").hasRole("ROLE_user")
                .and()
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests().anyRequest().permitAll()
                .and()
                .addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(((request, response, authException) -> {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType("application/json");
                    Utils.getObjectMapper().writeValue(
                            response.getOutputStream(), CommonResponse.fail(CustomErrorMessage.USER_FAIL_AUTHORIZATION));
                }))
                .accessDeniedHandler(((request, response, accessDeniedException) -> {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType("application/json");
                    Utils.getObjectMapper().writeValue(
                            response.getOutputStream(), CommonResponse.fail(CustomErrorMessage.USER_FAIL_AUTHORIZATION));
                })).and().build();
    }
}
