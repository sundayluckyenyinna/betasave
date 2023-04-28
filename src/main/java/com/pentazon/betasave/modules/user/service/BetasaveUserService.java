package com.pentazon.betasave.modules.user.service;

import com.pentazon.betasave.dto.OtpSendInfo;
import com.pentazon.betasave.modules.user.payload.request.*;
import com.pentazon.betasave.modules.user.payload.response.*;
import com.pentazon.betasave.utils.JwtUtil;
import com.pentazon.betasave.config.MessageProvider;
import com.pentazon.betasave.constants.*;
import com.pentazon.betasave.dto.ErrorResponse;
import com.pentazon.betasave.dto.PayloadResponse;
import com.pentazon.betasave.dto.ServerResponse;
import com.pentazon.betasave.modules.user.model.BetasaveUser;
import com.pentazon.betasave.modules.user.repository.IBetasaveUserRepository;
import com.pentazon.betasave.modules.user.repository.IUserPermissionRepository;
import com.pentazon.betasave.modules.user.repository.IUserRoleRepository;
import com.pentazon.betasave.modules.user.utils.PasswordUtil;
import com.pentazon.betasave.utils.OtpUtil;
import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class BetasaveUserService implements IBetasaveUserService{

    @Autowired
    private IBetasaveUserRepository betasaveUserRepository;

    @Autowired
    private MessageProvider messageProvider;

    @Autowired
    private PasswordUtil passwordUtil;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private IUserRoleRepository userRoleRepository;

    @Autowired
    private IUserPermissionRepository  userPermissionRepository;

    @Autowired
    private OtpUtil otpUtil;


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
        user.setUserRoleId(userRoleRepository.findByRoleName(RoleName.USER.name()).getId());  // DEFAULT USER
        user.setUsername(requestPayload.getUserName());
        user.setPhotoLink("http://localhost:8090/man.jpg");
        user.setCreatedBy(Creator.SYSTEM.name());
        user.setModifiedBy(Creator.SYSTEM.name());
        user.setDeviceId(requestPayload.getChannel().toUpperCase().equals(RequestChannel.MOBILE.name()) ? requestPayload.getDeviceId() : null);
        user.setGeoLocation(requestPayload.getGeoLocation());
        user.setLoginAttempt(0);
        user.setGender(requestPayload.getGender().toUpperCase());
        user.setChannel(requestPayload.getChannel());
        user.setAuthTokenCreatedDate(LocalDateTime.now());
        user.setAuthTokenExpirationDate(jwtUtil.getJWTExpiration(LocalDateTime.now())); // TODO
        user.setIsOtpVerified(false);
        user.setStatus(Status.UNVERIFIED.name());
        BetasaveUser savedUser = betasaveUserRepository.saveAndFlush(user);

        // Send OTP to the user email
        CompletableFuture
                .runAsync(() -> {
                    OtpSendInfo otpSendInfo = otpUtil.sendSignUpOtpToMail(requestPayload.getEmailAddress());
                    user.setOtp(passwordUtil.hashPassword(otpSendInfo.getOtpSent()));
                    user.setOtpCreatedDate(otpSendInfo.getCreatedDateTime());
                    user.setOtpExpDate(otpSendInfo.getExpirationDateTime());
                    betasaveUserRepository.save(user);
                });

        CreateUserResponsePayload responsePayload = new CreateUserResponsePayload();
        responsePayload.setAuthToken(savedUser.getAuthToken());
        responsePayload.setUsername(savedUser.getUsername());
        responsePayload.setCreatedAt(savedUser.getCreatedAt());
        responsePayload.setUpdatedAt(savedUser.getUpdatedAt());

        responseCode = ResponseCode.SUCCESS;
        responseMessage = messageProvider.getMessage(responseCode);
        PayloadResponse response = PayloadResponse.getInstance();
        response.setResponseCode(responseCode);
        response.setResponseMessage(responseMessage);
        response.setResponseData(responsePayload);

        return response;
    }

    @Override
    public ServerResponse loginUser(LoginUserRequestPayload requestPayload){
        String responseCode = ResponseCode.SYSTEM_ERROR;
        String responseMessage = messageProvider.getMessage(responseCode);
        ErrorResponse errorResponse = ErrorResponse.getInstance();

        // check if user exist from database -> done
        // If user doesn't exist, return status code 02
        BetasaveUser userByEmail = betasaveUserRepository.findByEmailAddress(requestPayload.getEmailAddress());
        if(userByEmail == null){
            responseCode = ResponseCode.RECORD_NOT_FOUND;
            responseMessage = messageProvider.getMessage(responseCode);
            errorResponse.setResponseCode(responseCode);
            errorResponse.setResponseMessage(responseMessage);
            return errorResponse;
        }

        // Check if account is active
        boolean isAccountLocked = userByEmail.getStatus().equalsIgnoreCase(Status.LOCKED.name());
        if(isAccountLocked){
            responseCode = ResponseCode.ACCOUNT_LOCKED;
            responseMessage = messageProvider.getMessage(responseCode);
            errorResponse.setResponseCode(responseCode);
            errorResponse.setResponseMessage(responseMessage);
            return errorResponse;
        }

        // validate password with bcrypt = done
        // return with invalid username or password if password isn't correct = done
        String getEncryptedPassword = userByEmail.getPassword();
        String getUsername = userByEmail.getUsername();
        boolean correctPassword = passwordUtil.isPasswordMatch(requestPayload.getPassword(),getEncryptedPassword);
        if (!correctPassword){
            responseCode = ResponseCode.INCORRECT_EMAIL_OR_PASSWORD;
            responseMessage = messageProvider.getMessage(responseCode);
            errorResponse.setResponseCode(responseCode);
            errorResponse.setResponseMessage(responseMessage);
            if(userByEmail.getLoginAttempt() >= 4){
                userByEmail.setStatus(Status.LOCKED.name());
            }
            userByEmail.setLoginAttempt(userByEmail.getLoginAttempt() + 1);
            betasaveUserRepository.save(userByEmail);
            return errorResponse;
        }

        // return jwt token if username and password is correct.
        BetasaveUser newUserLogin = userByEmail;
        LoginUserResponsePayload responsePayload = new LoginUserResponsePayload();

        newUserLogin.setAuthToken(jwtUtil.createJWTString(requestPayload.getEmailAddress()));
        // newUserLogin.setLoginAttempt(newUserLogin.getLoginAttempt()+1);
        newUserLogin.setLastLoginDate(LocalDateTime.now());

        BetasaveUser loginUser = betasaveUserRepository.save(newUserLogin);
        responsePayload.setAuthToken(loginUser.getAuthToken());
        responsePayload.setUpdatedAt(loginUser.getUpdatedAt());
        responsePayload.setUsername(loginUser.getUsername());
        responsePayload.setLastLoginDate(newUserLogin.getLastLoginDate());

        responseCode = ResponseCode.SUCCESS;
        responseMessage = messageProvider.getMessage(responseCode);
        PayloadResponse response = PayloadResponse.getInstance();
        response.setResponseCode(responseCode);
        response.setResponseMessage(responseMessage);
        response.setResponseData(responsePayload);

        return response;
    }


    @Override
    public ServerResponse getUser(String id) {
        String responseCode = ResponseCode.SYSTEM_ERROR;
        String responseMessage = this.messageProvider.getMessage(responseCode);
        ErrorResponse errorResponse = ErrorResponse.getInstance();

        BetasaveUser user = betasaveUserRepository.findByUserId(id);
        if (user == null){
            responseCode = ResponseCode.RECORD_NOT_FOUND;
            responseMessage = messageProvider.getMessage(responseCode);
            errorResponse.setResponseCode(responseCode);
            errorResponse.setResponseMessage(responseMessage);
            return errorResponse;
        }

        GetUserResponsePayload responsePayload = new GetUserResponsePayload();
        responsePayload.setUser(user);

        responseCode = ResponseCode.SUCCESS;
        responseMessage = messageProvider.getMessage(responseCode);
        PayloadResponse payloadResponse = new PayloadResponse();
        payloadResponse.setResponseCode(responseCode);
        payloadResponse.setResponseMessage(responseMessage);
        payloadResponse.setResponseData(responsePayload);
        return payloadResponse;
    }

    @Override
    public ServerResponse resetUserPassword(ResetPasswordRequestPayload requestPayload) {
        String responseCode = ResponseCode.SYSTEM_ERROR;
        String responseMessage = this.messageProvider.getMessage(responseCode);
        ErrorResponse errorResponse = ErrorResponse.getInstance();

        //verify user by email in database
        BetasaveUser user = betasaveUserRepository.findByEmailAddress(requestPayload.getEmailAddress());
        if(user == null){
            responseCode = ResponseCode.RECORD_NOT_FOUND;
            responseMessage = messageProvider.getMessage(responseCode);
            errorResponse.setResponseCode(responseCode);
            errorResponse.setResponseMessage(responseMessage);
            return errorResponse;
        }
        //verify old password
        boolean correctOldPassword = passwordUtil.isPasswordMatch(requestPayload.getOldPassword(), user.getPassword());
        if (!correctOldPassword){
            responseCode = ResponseCode.INCORRECT_PASSWORD;
            responseMessage = messageProvider.getMessage(responseCode);
            errorResponse.setResponseCode(responseCode);
            errorResponse.setResponseMessage(responseMessage);
            return errorResponse;
        }
        //update password in database
        user.setPassword(passwordUtil.hashPassword(requestPayload.getNewPassword()));
        user.setUpdatedAt(LocalDateTime.now());
        betasaveUserRepository.saveAndFlush(user);
        ResetPasswordResponsePayload responsePayload = new ResetPasswordResponsePayload();
        responsePayload.setUsername(user.getUsername());
        responsePayload.setUpdatedAt(user.getUpdatedAt());

        responseCode = ResponseCode.SUCCESS;
        responseMessage = messageProvider.getMessage(responseCode);
        PayloadResponse response = PayloadResponse.getInstance();
        response.setResponseCode(responseCode);
        response.setResponseMessage(responseMessage);
        response.setResponseData(responsePayload);
        return response;
    }

    @Override
    public ServerResponse verifyOtp(OtpVerificationRequestPayload requestPayload, String authToken){
        String responseCode = ResponseCode.SYSTEM_ERROR;
        String responseMessage = this.messageProvider.getMessage(responseCode);
        ErrorResponse errorResponse = ErrorResponse.getInstance();

        String otpFromUser = requestPayload.getOtp();
        String userEmail = jwtUtil.getUserEmailFromJWTToken(authToken);
        BetasaveUser user = betasaveUserRepository.findByEmailAddress(userEmail);

        // Check for the existence of the user in the system
        if(user == null) {
            responseCode = ResponseCode.RECORD_NOT_FOUND;
            responseMessage = messageProvider.getMessage(responseCode);
            errorResponse.setResponseCode(responseCode);
            errorResponse.setResponseMessage(responseMessage);
            return errorResponse;
        }

        // Check if Otp is already verified.
        if(user.getIsOtpVerified()){
            responseCode = ResponseCode.OTP_ALREADY_VERIFIED;
            responseMessage = messageProvider.getMessage(responseCode);
            errorResponse.setResponseCode(responseCode);
            errorResponse.setResponseMessage(responseMessage);
            return errorResponse;
        }

        // Check if otp has expired
        if(user.getOtpExpDate().isBefore(LocalDateTime.now())){
            responseCode = ResponseCode.OTP_EXPIRED;
            responseMessage = messageProvider.getMessage(responseCode);
            errorResponse.setResponseCode(responseCode);
            errorResponse.setResponseMessage(responseMessage);
            return errorResponse;
        }

        // Check for the correctness of the otp
        boolean isOtpCorrect = passwordUtil.isPasswordMatch(otpFromUser, user.getOtp());
        if(!isOtpCorrect){
            responseCode = ResponseCode.OTP_INCORRECT;
            responseMessage = messageProvider.getMessage(responseCode);
            errorResponse.setResponseCode(responseCode);
            errorResponse.setResponseMessage(responseMessage);
            return errorResponse;
        }

        // Update the user status
        user.setStatus(Status.ACTIVE.name());
        user.setIsOtpVerified(true);
        user.setIsVerified(true);
        betasaveUserRepository.saveAndFlush(user);

        // Create response to the application client.
        OtpVerificationResponsePayload responsePayload = new OtpVerificationResponsePayload();
        responsePayload.setUserStatus(user.getStatus());
        responsePayload.setVerifiedDateTime(LocalDateTime.now());
        responsePayload.setCreatedDateTime(user.getOtpCreatedDate());

        PayloadResponse payloadResponse = PayloadResponse.getInstance();
        payloadResponse.setResponseCode(ResponseCode.SUCCESS);
        payloadResponse.setResponseMessage(messageProvider.getMessage(ResponseCode.SUCCESS));
        payloadResponse.setResponseData(responsePayload);
        return payloadResponse;
    }

    @Override
    public ServerResponse lockAccount(String id, LockAccountRequestPayload requestPayload) {
        String responseCode = ResponseCode.SYSTEM_ERROR;
        String responseMessage = messageProvider.getMessage(responseCode);
        ErrorResponse errorResponse = ErrorResponse.getInstance();

        BetasaveUser user = betasaveUserRepository.findByUserId(id);
        if (user == null){
            responseCode = ResponseCode.RECORD_NOT_FOUND;
            responseMessage = messageProvider.getMessage(responseCode);
            errorResponse.setResponseCode(responseCode);
            errorResponse.setResponseMessage(responseMessage);
            return errorResponse;
        }

        boolean correctPassword = passwordUtil.isPasswordMatch(requestPayload.getPassword(), user.getPassword());
        if (!correctPassword){
            responseCode = ResponseCode.INCORRECT_PASSWORD;
            responseMessage = messageProvider.getMessage(responseCode);
            errorResponse.setResponseCode(responseCode);
            errorResponse.setResponseMessage(responseMessage);
            return errorResponse;
        }

        if (user.getStatus() == Status.LOCKED.name()){
            responseCode = ResponseCode.USER_ACCOUNT_AlREADY_LOCKED;
            responseMessage = messageProvider.getMessage(responseCode);
            errorResponse.setResponseCode(responseCode);
            errorResponse.setResponseMessage(responseMessage);
            return errorResponse;
        }

        user.setStatus(Status.LOCKED.name());
        user.setUpdatedAt(LocalDateTime.now());
        betasaveUserRepository.saveAndFlush(user);

        LockAccountResponsePayload responsePayload = new LockAccountResponsePayload();
        responsePayload.setStatus(user.getStatus());
        responsePayload.setUpdatedAt(user.getUpdatedAt());

        PayloadResponse payloadResponse = PayloadResponse.getInstance();
        payloadResponse.setResponseCode(ResponseCode.SUCCESS);
        payloadResponse.setResponseMessage(messageProvider.getMessage(ResponseCode.SUCCESS));
        payloadResponse.setResponseData(responsePayload);
        return payloadResponse;
    }
}
