package com.pentazon.betasave.modules.user.service;

import com.pentazon.betasave.dto.ServerResponse;
import com.pentazon.betasave.modules.user.payload.request.CreateUserRequestPayload;
import com.pentazon.betasave.modules.user.payload.request.LoginUserRequestPayload;
import com.pentazon.betasave.modules.user.payload.request.OtpVerificationRequestPayload;
import org.springframework.stereotype.Service;

@Service
public interface IBetasaveUserService
{
    ServerResponse createUser(CreateUserRequestPayload requestPayload);
    ServerResponse loginUser(LoginUserRequestPayload requestPayload);
    ServerResponse verifyOtp(OtpVerificationRequestPayload requestPayload, String authToken);
}
