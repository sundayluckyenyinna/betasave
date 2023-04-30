package com.pentazon.betasave.modules.user.payload.request;

import lombok.Data;

@Data
public class VerifyForgetPasswordOtpRequestPayload {
        private String otp;
        private String newPassword;
}
