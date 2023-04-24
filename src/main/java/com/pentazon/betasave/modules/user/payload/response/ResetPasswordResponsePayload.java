package com.pentazon.betasave.modules.user.payload.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResetPasswordResponsePayload {
    private String username;
    private LocalDateTime updatedAt;
}
