package com.pentazon.betasave.modules.user.payload.request;

import lombok.Data;

@Data
public class ForgetUserPasswordRequestPayload {
    private String emailAddress;
}
