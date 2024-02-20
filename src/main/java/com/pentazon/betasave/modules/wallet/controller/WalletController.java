package com.pentazon.betasave.modules.wallet.controller;

import com.pentazon.betasave.dto.ServerResponse;
import com.pentazon.betasave.modules.transactions.service.ITransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Tag(name = "Wallet Service and Management", description = "Web API service for the management of user wallet")
@RestController
@RequestMapping("/wallet")
public class WalletController
{
    @Autowired
    private ITransactionService walletService;

    @GetMapping(value = "/initialize", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServerResponse> handleInitWalletFunding(@RequestParam("amount") String amount, @RequestParam("currency") String currency, @RequestHeader("Authorization") String authToken)
    {
        // Call the service
        ServerResponse response = walletService.sendPaystackAuthorizationUrl(amount, currency, authToken);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/fund/verify")
    public ResponseEntity<ServerResponse> handleInitPaystackCallback(@RequestParam Map<String, String> params   ){
        ServerResponse response = walletService.handlePaystackCallback(params);
        return null;
    }
}
