package com.pentazon.betasave.modules.wallet.controller;

import com.pentazon.betasave.dto.ServerResponse;
import com.pentazon.betasave.modules.wallet.service.IWalletService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Wallet Service and Management", description = "Web API service for the management of user wallet")
@RestController
@RequestMapping("/wallet")
public class WalletController
{
    @Autowired
    private IWalletService walletService;

    @GetMapping(value = "/initialize", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServerResponse> handleInitWalletFunding(@RequestParam("amount") String amount, @RequestParam("currency") String currency, @RequestHeader("Authorization") String authToken)
    {
        // Call the service
        ServerResponse response = walletService.sendPaystackAuthorizationUrl(amount, currency, authToken);
        return ResponseEntity.ok(response);
    }
}
