package com.temp.backend.auth.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthenticationResponse {

    private String message;
    private String token;

    public static AuthenticationResponse of(String token, String message) {
        return new AuthenticationResponse(message, token);
    }
}
