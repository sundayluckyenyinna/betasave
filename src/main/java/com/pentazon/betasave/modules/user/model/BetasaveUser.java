package com.pentazon.betasave.modules.user.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "bs_user")
@Getter
@Setter
public class BetasaveUser
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "email_verified_at")
    private LocalDateTime emailVerifiedAt;

    @Column(name = "password")
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "address2")
    private String address2;

    @Column(name = "mobile_number")
    private String mobileNUmber;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "auth_token")
    private String authToken;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "verify_submit_status")
    private String verifySubmitStatus;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "locale")
    private String locale;

    @Column(name = "user_role_id")
    private Long userRoleId;

    @Column(name = "username")
    private String username;

    @Column(name = "photo_link")
    private String photoLink;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "geo_location")
    private String geoLocation;

    @Column(name = "login_attempt")
    private Integer loginAttempt;

    @Column(name = "gender")
    private String gender;

    @Column(name = "channel")
    private String channel;

    @Column(name = "auth_token_created_date")
    private LocalDateTime authTokenCreatedDate;

    @Column(name = "auth_token_expiration_date")
    private LocalDateTime authTokenExpirationDate;

    @Column(name = "otp")
    private String otp;

    @Column(name = "otp_created_date")
    private LocalDateTime otpCreatedDate;

    @Column(name = "otp_exp_date")
    private LocalDateTime otpExpDate;

    @Column(name = "is_otp_verified")
    private Boolean isOtpVerified;

    @Column(name = "status")
    private String status;

}
