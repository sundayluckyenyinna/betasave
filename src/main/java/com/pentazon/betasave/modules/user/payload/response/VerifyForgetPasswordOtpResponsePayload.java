package com.pentazon.betasave.modules.user.payload.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class VerifyForgetPasswordOtpResponsePayload {
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private String authToken;
}
