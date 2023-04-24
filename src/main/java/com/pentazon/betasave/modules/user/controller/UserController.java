package com.pentazon.betasave.modules.user.controller;

import com.pentazon.betasave.dto.ServerResponse;
import com.pentazon.betasave.modules.user.payload.request.CreateUserRequestPayload;
import com.pentazon.betasave.modules.user.payload.request.LoginUserRequestPayload;
import com.pentazon.betasave.modules.user.payload.request.OtpVerificationRequestPayload;
import com.pentazon.betasave.modules.user.payload.request.ResetPasswordRequestPayload;
import com.pentazon.betasave.modules.user.service.IBetasaveUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController
{

    @Autowired
    private IBetasaveUserService betasaveUserService;

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServerResponse> handleBetasaveUserSignup(@RequestBody CreateUserRequestPayload requestPayload){
        // TODO: Validation of the request body.
        // Call the service
        ServerResponse serverResponse = betasaveUserService.createUser(requestPayload);
        return ResponseEntity.ok(serverResponse);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<ServerResponse> handleBetasaveLogin(@RequestBody LoginUserRequestPayload payload) {
        // TODO: validate request body
        // TODO: If user doesn't exist, return status code 02
        ServerResponse serverResponse = betasaveUserService.loginUser(payload);
        return ResponseEntity.ok(serverResponse);
    }

    @PostMapping(value = "/verify-otp", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServerResponse> handleVerifyOtp(@RequestBody OtpVerificationRequestPayload requestPayload, @RequestHeader("Authorization") String jwtToken){
        ServerResponse serverResponse = betasaveUserService.verifyOtp(requestPayload, jwtToken);
        return ResponseEntity.ok(serverResponse);
    }

    @PostMapping(value = "/reset-password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServerResponse> handleResetPassword(@RequestBody ResetPasswordRequestPayload requestPayload){
        ServerResponse serverResponse = betasaveUserService.resetUserPassword(requestPayload);
        return ResponseEntity.ok(serverResponse);
    }
}
