package com.pentazon.betasave.modules.user.service;

import com.pentazon.betasave.dto.ServerResponse;
import com.pentazon.betasave.modules.user.payload.request.CreateUserRequestPayload;

public interface IBetasaveUserService
{
    ServerResponse createUser(CreateUserRequestPayload requestPayload);
}
