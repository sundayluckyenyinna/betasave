package com.pentazon.betasave.modules.wallet.service;

import com.pentazon.betasave.config.MessageProvider;
import com.pentazon.betasave.constants.ResponseCode;
import com.pentazon.betasave.constants.Status;
import com.pentazon.betasave.modules.user.model.BetasaveUser;
import com.pentazon.betasave.modules.user.repository.IBetasaveUserRepository;
import com.pentazon.betasave.modules.wallet.model.BetasaveWallet;
import com.pentazon.betasave.modules.wallet.payload.data.WalletOperationResult;
import com.pentazon.betasave.modules.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletService implements IWalletService
{

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private MessageProvider messageProvider;

    @Autowired
    private IBetasaveUserRepository userRepository;


    @Override
    public WalletOperationResult processCreditWallet(BetasaveWallet wallet, String amount) {
        WalletOperationResult result = new WalletOperationResult();
        String code;
        result.setHasError(true);

        if(wallet == null){
            code = ResponseCode.RECORD_NOT_FOUND;
            result.setResponseMessage(messageProvider.getMessage(code).concat(" Wallet not found."));
            result.setResponseCode(code);
            return result;
        }

        String userEmail = wallet.getUserEmail();
        BetasaveUser walletOwner = userRepository.findByEmailAddress(userEmail);
        if(walletOwner == null){
            code = ResponseCode.RECORD_NOT_FOUND;
            result.setResponseMessage(messageProvider.getMessage(code));
            result.setResponseCode(code);
            return result;
        }

        BigDecimal amountDec = new BigDecimal(amount);
        BigDecimal initialWalletBalance = wallet.getBalance();
        BigDecimal newBalance = amountDec.add(initialWalletBalance);

        wallet.setBalance(newBalance);
        walletRepository.saveAndFlush(wallet);

        code = ResponseCode.SUCCESS;
        result.setHasError(false);
        result.setResponseCode(code);
        result.setResponseMessage(messageProvider.getMessage(code));
        return result;
    }

    @Override
    public WalletOperationResult processDebitWallet(BetasaveWallet wallet, String amount) {
        WalletOperationResult result = new WalletOperationResult();
        String code;
        result.setHasError(true);

        //check if wallet is passed
        if(wallet == null){
            code = ResponseCode.RECORD_NOT_FOUND;
            result.setResponseCode(code);
            result.setResponseMessage(messageProvider.getMessage(code).concat("Wallet not found."));
            return result;
        }

        //check if user wallet exist
        String walletEmail = wallet.getUserEmail();
        if(walletEmail == null){
            code = ResponseCode.RECORD_NOT_FOUND;
            result.setResponseCode(code);
            result.setResponseMessage(messageProvider.getMessage(code).concat("User wallet not found."));
            return result;
        }
        //check if account is locked
        BetasaveUser user = userRepository.findByEmailAddress(walletEmail);
        if (user.getStatus() == Status.LOCKED.name()){
            code = ResponseCode.USER_LOCKED_ACCOUNT;
            result.setResponseCode(code);
            result.setResponseMessage(messageProvider.getMessage(code));
            return result;
        }
        //check if account to be debited exceed amount in account
        BigDecimal amountDec = new BigDecimal(amount);
        BigDecimal initialWalletBalance = wallet.getBalance();
        if (initialWalletBalance.compareTo(amountDec) < 0){
            code = ResponseCode.INSUFFICIENT_ACCOUNT_BALANCE;
            result.setResponseCode(code);
            result.setResponseMessage(messageProvider.getMessage(code));
            return result;
        }
        //debit account, save and flush new balance
        BigDecimal newBalance = initialWalletBalance.subtract(amountDec);
        wallet.setBalance(newBalance);
        walletRepository.saveAndFlush(wallet);

        //return new balance
        code = ResponseCode.SUCCESS;
        result.setHasError(false);
        result.setResponseCode(code);
        result.setResponseMessage(messageProvider.getMessage(code));

        return result;
    }
}
