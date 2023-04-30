package com.pentazon.betasave.modules.wallet.service;

import com.pentazon.betasave.dto.ServerResponse;
import org.springframework.stereotype.Service;

@Service
public interface IWalletService
{

    ServerResponse sendPaystackAuthorizationUrl(String amount, String currency, String token);
}
