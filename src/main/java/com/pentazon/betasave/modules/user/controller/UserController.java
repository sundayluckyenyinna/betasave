package com.pentazon.betasave.modules.user.controller;


import com.google.gson.Gson;
import com.pentazon.betasave.dto.ServerResponse;
import com.pentazon.betasave.modules.user.payload.request.CreateUserRequestPayload;
import com.pentazon.betasave.modules.user.service.IBetasaveUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController
{

    @Autowired
    private IBetasaveUserService betasaveUserService;

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> handleBetasaveUserSignup(@RequestBody CreateUserRequestPayload requestPayload){
        // TODO: Validation of the request body.
        // Call the service
        ServerResponse serverResponse = betasaveUserService.createUser(requestPayload);
        return ResponseEntity.ok(serverResponse);
    }
}
