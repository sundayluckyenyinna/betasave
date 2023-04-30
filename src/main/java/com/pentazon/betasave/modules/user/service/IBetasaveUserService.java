package com.pentazon.betasave.modules.user.service;

import com.pentazon.betasave.dto.ServerResponse;
import com.pentazon.betasave.modules.user.payload.request.*;
import org.springframework.stereotype.Service;

@Service
public interface IBetasaveUserService
{
    ServerResponse createUser(CreateUserRequestPayload requestPayload);
    ServerResponse loginUser(LoginUserRequestPayload requestPayload);
    ServerResponse getUser(String id);
    ServerResponse forgetUserPassword(ForgetUserPasswordRequestPayload requestPayload);
    ServerResponse verifyForgetUserPasswordOtp(VerifyForgetPasswordOtpRequestPayload requestPayload, String authToken);
    ServerResponse resetUserPassword(ResetPasswordRequestPayload requestPayload);
    ServerResponse verifyOtp(OtpVerificationRequestPayload requestPayload, String authToken);
    ServerResponse lockAccount(LockAccountRequestPayload requestPayload, String token);
}
