package com.pentazon.betasave.modules.user.payload.request;

import lombok.Data;

@Data
public class ResetPasswordRequestPayload {
    private String emailAddress;
    private String oldPassword;
    private String newPassword;
}
