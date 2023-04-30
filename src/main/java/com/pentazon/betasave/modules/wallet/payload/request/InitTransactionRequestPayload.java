package com.pentazon.betasave.modules.wallet.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class InitTransactionRequestPayload
{
    private String amount;
    private String email;
    private String currency;
    private String reference;

    @SerializedName("callback_url")  // For Gson library
    @JsonProperty("callback_url")    // For springBoot.
    private String callbackUrl;

    private List<String> channels = List.of("card", "bank", "bank_transfer");

}
