package org.example.common.auth.jwt;

import lombok.Getter;

import java.util.List;

/**
 * record ?
 * record -> jdk 16 부터 정식 스펙
 * 모든 필드가 final
 * 암묵적으로 상속 안함, 다른 클래스 상속 불가, 구현은 가능
 * 생성자, toString, equals, hashCode 자동 생성
 */
public record TokenInfo(Long id, String userName, List<String> roles) {
    public TokenInfo {
        // 필드에 조건이 필요하면 여기에 조건문 추가하면 됨
    }
}
