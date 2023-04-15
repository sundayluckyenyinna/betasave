package com.pentazon.betasave.modules.user.service;

import com.pentazon.betasave.config.JwtUtil;
import com.pentazon.betasave.config.MessageProvider;
import com.pentazon.betasave.constants.*;
import com.pentazon.betasave.dto.ErrorResponse;
import com.pentazon.betasave.dto.ServerResponse;
import com.pentazon.betasave.modules.user.model.BetasaveUser;
import com.pentazon.betasave.modules.user.payload.request.CreateUserRequestPayload;
import com.pentazon.betasave.modules.user.payload.response.CreateUserResponsePayload;
import com.pentazon.betasave.modules.user.repository.IBetasaveUserRepository;
import com.pentazon.betasave.modules.user.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class BetasaveUserService implements IBetasaveUserService{

    @Autowired
    private IBetasaveUserRepository betasaveUserRepository;

    @Autowired
    private MessageProvider messageProvider;

    @Autowired
    private PasswordUtil passwordUtil;

    @Autowired
    private JwtUtil jwtUtil;


    @Override
    public ServerResponse createUser(CreateUserRequestPayload requestPayload) {
        String responseCode = ResponseCode.SYSTEM_ERROR;
        String responseMessage = messageProvider.getMessage(responseCode);
        ErrorResponse errorResponse = ErrorResponse.getInstance();

        // Check if the user already exist in database by email, username and phone number
        BetasaveUser userByEmail = betasaveUserRepository.findByEmailAddress(requestPayload.getEmailAddress());
        if(userByEmail != null){
            responseCode = ResponseCode.RECORD_ALREADY_EXIST_BY_EMAIL;
            responseMessage = messageProvider.getMessage(responseCode);
            errorResponse.setResponseCode(responseCode);
            errorResponse.setResponseMessage(responseMessage);
            return errorResponse;
        }

        // Check if the user exists by username
        BetasaveUser userByUsername = betasaveUserRepository.findByUsername(requestPayload.getUserName());
        if(userByUsername != null){
            responseCode = ResponseCode.RECORD_ALREADY_EXIST_BY_USERNAME;
            responseMessage = messageProvider.getMessage(responseCode);
            errorResponse.setResponseCode(responseCode);
            errorResponse.setResponseMessage(responseMessage);
            return errorResponse;
        }

        // Check if the user already exists by mobileNumber.
        BetasaveUser userByMobileNumber = betasaveUserRepository.findByMobileNUmber(requestPayload.getMobileNumber());
        if(userByMobileNumber != null){
            responseCode = ResponseCode.RECORD_ALREADY_EXIST_BY_MOBILE_NUMBER;
            responseMessage = messageProvider.getMessage(responseCode);
            errorResponse.setResponseCode(responseCode);
            errorResponse.setResponseMessage(responseMessage);
            return errorResponse;
        }

        // Check for the deviceId
        if(requestPayload.getChannel().equalsIgnoreCase(RequestChannel.MOBILE.name())){
            if(requestPayload.getDeviceId() == null || requestPayload.getDeviceId().isEmpty() || requestPayload.getDeviceId().isBlank()){
                responseCode = ResponseCode.DEVICE_ID_NOT_FOUND;
                responseMessage = messageProvider.getMessage(responseCode);
                errorResponse.setResponseCode(responseCode);
                errorResponse.setResponseMessage(responseMessage);
                return errorResponse;
            }
        }

        // Create the user entity
        BetasaveUser user = new BetasaveUser();
        user.setUserId(UUID.randomUUID().toString());
        user.setFirstName(requestPayload.getFirstName());
        user.setMiddleName(requestPayload.getMiddleName());
        user.setLastName(requestPayload.getLastName());
        user.setEmailAddress(requestPayload.getEmailAddress());
        user.setPassword(passwordUtil.hashPassword(requestPayload.getPassword()));
        user.setMobileNUmber(requestPayload.getMobileNumber());
        user.setLastLoginDate(LocalDateTime.now());
        user.setAuthToken(jwtUtil.createJWTString(requestPayload.getEmailAddress()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setVerifySubmitStatus("");
        user.setIsVerified(false);
        user.setBusinessName(requestPayload.getBusinessName());
        user.setLocale(Locale.ENGLISH.name());
        user.setUserRoleId(Long.parseLong("0"));  // TODO
        user.setUsername(requestPayload.getUserName());
        user.setPhotoLink(""); // TODO
        user.setCreatedBy(Creator.SYSTEM.name());
        user.setModifiedBy(Creator.SYSTEM.name());
        user.setDeviceId(requestPayload.getChannel().toUpperCase().equals(RequestChannel.MOBILE.name()) ? requestPayload.getDeviceId() : null);
        user.setGeoLocation(requestPayload.getGeoLocation());
        user.setLoginAttempt(Long.parseLong("0"));
        user.setGender(requestPayload.getGender().toUpperCase());
        user.setChannel(requestPayload.getChannel());
        user.setAuthTokenCreatedDate(LocalDateTime.now());
        user.setAuthTokenExpirationDate(jwtUtil.getJWTExpiration(LocalDateTime.now())); // TODO
        user.setIsOtpVerified(false);
        user.setStatus(Status.UNVERIFIED.name());

        return null;
    }
}
