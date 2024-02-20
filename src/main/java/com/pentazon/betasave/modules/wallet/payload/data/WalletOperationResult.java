package com.pentazon.betasave.modules.wallet.payload.data;

import lombok.Data;

@Data
public class WalletOperationResult
{
    private String responseCode;
    private String responseMessage;
    private boolean isHasError;
}
