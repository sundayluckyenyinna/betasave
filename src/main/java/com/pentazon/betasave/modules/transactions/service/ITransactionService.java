package com.pentazon.betasave.modules.transactions.service;

import com.pentazon.betasave.dto.ServerResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ITransactionService
{

    ServerResponse sendPaystackAuthorizationUrl(String amount, String currency, String token);
    ServerResponse handlePaystackCallback(Map<String, String> ref);
}
