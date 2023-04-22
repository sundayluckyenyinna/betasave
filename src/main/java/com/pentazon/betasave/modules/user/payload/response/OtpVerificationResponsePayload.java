package com.pentazon.betasave.modules.user.payload.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class OtpVerificationResponsePayload
{
    private LocalDateTime createdDateTime;
    private LocalDateTime verifiedDateTime;
    private String userStatus;
}
