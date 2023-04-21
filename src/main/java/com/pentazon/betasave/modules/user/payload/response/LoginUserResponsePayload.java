package com.pentazon.betasave.modules.user.payload.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginUserResponsePayload {

    private String username;

    private String authToken;
    private LocalDateTime lastLoginDate;
    private LocalDateTime updatedAt;
}
