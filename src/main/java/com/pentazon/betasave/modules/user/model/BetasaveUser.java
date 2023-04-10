package com.pentazon.betasave.modules.user.model;

import org.apache.tomcat.jni.Local;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "bt_user")
public class BetasaveUser
{
    @Id
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastNmae;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "email_verified_at")
    private LocalDate emailVerifiedAt;

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
    private Number zipCode;

    @Column(name = "last_login_date")
    private LocalDate lastLoginDate;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    @Column(name = "auth_token")
    private String authToken;

    @Column(name = "createdAt")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name = "verify_submit_status")
    private String verifySubmitStatus;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "locale")
    private String locale;

    @Column(name = "user_role_id")
    private Number userRoleId;

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
    private Number loginAttempt;

    @Column(name = "gender")
    private String gender;

    @Column(name = "channel")
    private String channel;

    @Column(name = "auth_token_created_date")
    private LocalDate authTokenCreatedDate;

    @Column(name = "auth_token_expiration_date")
    private LocalDate authTokenExpirationDate;

    @Column(name = "otp")
    private String otp;

    @Column(name = "otp_created_date")
    private Date otpCreatedDate;

    @Column(name = "otp_exp_date")
    private LocalDate otpExpDate;

    @Column(name = "is_otp_verified")
    private Boolean isOtpVerified;

    @Column(name = "status")
    private String status;
}
