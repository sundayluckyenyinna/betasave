package com.pentazon.betasave.modules.web;


import com.google.gson.Gson;
import com.pentazon.betasave.constants.ResponseCode;
import com.pentazon.betasave.dto.ErrorResponse;
import kong.unirest.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class WebClient
{
    private static final Gson gson = new Gson();


    public static String getForObject(String url, Map<String, Object> params, Map<String, String> headers){
        ErrorResponse errorResponse = ErrorResponse.getInstance();
//        Unirest.config().verifySsl(false);
        try{
            GetRequest getRequest = Unirest.get(url);
            if(params != null)
                getRequest.queryString(params);
            if(headers != null)
                getRequest.headers(headers);
            return getRequest.asString().getBody();
        }
        catch (UnirestException exception) {
            log.error("Server connection or client error: {}", exception.getMessage());
            errorResponse.setResponseCode(ResponseCode.THIRD_PARTY_FAILURE);
            errorResponse.setResponseMessage(exception.getMessage());
            return gson.toJson(errorResponse);
        }
    }

    public static String postForObject(String url, String body, Map<String, Object> params, Map<String, String> headers)
    {
        PaystackWebResponse paystackWebResponse = new PaystackWebResponse();
        paystackWebResponse.setConnectionError(false);

        try{
            RequestBodyEntity postRequest = Unirest.post(url).body(body);
            if(params != null)
                postRequest.queryString(params);
            if(headers != null)
                postRequest.headers(headers);

            String webDataString = postRequest.asString().getBody();
            paystackWebResponse.setWebDataString(webDataString);

            log.info("Paystack response: {}", webDataString);
            return gson.toJson(paystackWebResponse);
        }
        catch (UnirestException exception) {
            log.error("Server connection or client error: {}", exception.getMessage());
            paystackWebResponse.setMessage(exception.getMessage());
            paystackWebResponse.setStatus(false);
            paystackWebResponse.setConnectionError(true);
            return gson.toJson(paystackWebResponse);
        }
    }
}
