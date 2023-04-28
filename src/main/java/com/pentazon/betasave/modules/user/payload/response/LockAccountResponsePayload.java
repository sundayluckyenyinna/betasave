package com.pentazon.betasave.modules.user.payload.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LockAccountResponsePayload {
    private String status;
    private LocalDateTime updatedAt;
}
