package com.pentazon.betasave.dto;

import lombok.Data;

@Data
public class ErrorResponse implements ServerResponse
{
    private String responseCode;
    private String responseMessage;
    public static ErrorResponse getInstance(){
        return new ErrorResponse();
    }
}
