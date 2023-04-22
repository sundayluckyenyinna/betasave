package com.pentazon.betasave.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OtpSendInfo
{
    private String otpSent;
    private LocalDateTime createdDateTime;
    private LocalDateTime expirationDateTime;
    private String recipientEmail;
}
