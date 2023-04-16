package com.pentazon.betasave.modules.user.payload.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateUserResponsePayload
{
    private String username;
    private String authToken;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
