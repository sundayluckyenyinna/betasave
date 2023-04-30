package com.pentazon.betasave.modules.wallet.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class InitTransactionResponseData
{
    @SerializedName("authorization_url")
    @JsonProperty("authorization_url")
    private String authorizationUrl;

    @SerializedName("access_code")
    @JsonProperty("access_code")
    private String accessCode;

    private String reference;
}
