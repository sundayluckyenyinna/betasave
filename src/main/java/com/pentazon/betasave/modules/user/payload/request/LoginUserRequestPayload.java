package com.pentazon.betasave.modules.user.payload.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginUserRequestPayload {

    private String userName;

    private String emailAddress;

    private String password;

}
