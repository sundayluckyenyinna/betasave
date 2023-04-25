package com.pentazon.betasave.modules.user.payload.response;

import com.pentazon.betasave.modules.user.model.BetasaveUser;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GetUserResponsePayload {
    private BetasaveUser user;
    private String userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String emailAddress;
    private LocalDateTime emailVerifiedAt;
    private String password;
    private String address;
    private String address2;
    private String mobileNUmber;
    private String dateOfBirth;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private LocalDateTime lastLoginDate;
    private LocalDateTime deletedAt;
    private String authToken;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String verifySubmitStatus;
    private Boolean isVerified;
    private String businessName;
    private String locale;
    private Long userRoleId;
    private String username;
    private String photoLink;
    private String createdBy;
    private String modifiedBy;
    private String deviceId;
    private String geoLocation;
    private Integer loginAttempt;
    private String gender;
    private String channel;
    private LocalDateTime authTokenCreatedDate;
    private LocalDateTime authTokenExpirationDate;
    private LocalDateTime otpCreatedDate;
    private String status;
}
