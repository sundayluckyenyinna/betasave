package com.pentazon.betasave.modules.user.service;

import com.pentazon.betasave.dto.ServerResponse;
import com.pentazon.betasave.modules.user.payload.request.CreateUserRequestPayload;
import org.springframework.stereotype.Service;

@Service
public interface IBetasaveUserService
{
    ServerResponse createUser(CreateUserRequestPayload requestPayload);
}
