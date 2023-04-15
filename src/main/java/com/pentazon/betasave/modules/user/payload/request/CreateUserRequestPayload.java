package com.pentazon.betasave.modules.user.payload.request;

import lombok.Data;

@Data
public class CreateUserRequestPayload
{
    private String firstName;
    private String middleName;
    private String lastName;
    private String emailAddress;
    private String password;
    private String mobileNumber;
    private String businessName;
    private String userName;
    private String deviceId;
    private String geoLocation;
    private String gender;
    private String channel;
}
