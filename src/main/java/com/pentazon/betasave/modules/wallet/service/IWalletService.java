package com.pentazon.betasave.modules.wallet.service;

import com.pentazon.betasave.dto.ServerResponse;
import com.pentazon.betasave.modules.wallet.model.BetasaveWallet;
import com.pentazon.betasave.modules.wallet.payload.data.WalletOperationResult;

public interface IWalletService
{
    WalletOperationResult processCreditWallet(BetasaveWallet wallet, String amount);
    WalletOperationResult processDebitWallet(BetasaveWallet wallet, String amount);
}
