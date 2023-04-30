package com.pentazon.betasave.modules.wallet.payload.response;

import lombok.Data;

@Data
public class InitTransactionResponsePayload
{
    private boolean status;
    private String message;
    private InitTransactionResponseData data;
}
