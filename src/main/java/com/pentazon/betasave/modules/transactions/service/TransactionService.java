package com.pentazon.betasave.modules.transactions.service;

import com.google.gson.Gson;
import com.pentazon.betasave.config.MessageProvider;
import com.pentazon.betasave.constants.ResponseCode;
import com.pentazon.betasave.dto.ErrorResponse;
import com.pentazon.betasave.dto.PayloadResponse;
import com.pentazon.betasave.dto.ServerResponse;
import com.pentazon.betasave.modules.transactions.constants.PaymentGateway;
import com.pentazon.betasave.modules.transactions.model.TransactionInit;
import com.pentazon.betasave.modules.transactions.model.VerifiedTransactions;
import com.pentazon.betasave.modules.transactions.repository.TransactionInitRepository;
import com.pentazon.betasave.modules.user.model.BetasaveUser;
import com.pentazon.betasave.modules.user.repository.IBetasaveUserRepository;
import com.pentazon.betasave.modules.wallet.dto.InitTransactionResponseDTO;
import com.pentazon.betasave.modules.wallet.model.BetasaveWallet;
import com.pentazon.betasave.modules.wallet.payload.data.WalletOperationResult;
import com.pentazon.betasave.modules.wallet.payload.request.InitTransactionRequestPayload;
import com.pentazon.betasave.modules.wallet.payload.response.InitTransactionResponsePayload;
import com.pentazon.betasave.modules.wallet.repository.WalletRepository;
import com.pentazon.betasave.modules.wallet.service.IWalletService;
import com.pentazon.betasave.modules.wallet.util.WalletUtils;
import com.pentazon.betasave.modules.web.PaystackWebResponse;
import com.pentazon.betasave.modules.web.WebClient;
import com.pentazon.betasave.utils.JwtUtil;
import com.pentazon.betasave.utils.OtaUtilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class TransactionService implements ITransactionService
{

    @Autowired
    private Environment environment;


    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private IBetasaveUserRepository betasaveUserRepository;

    @Autowired
    private TransactionInitRepository transactionInitRepository;

    @Autowired
    private MessageProvider messageProvider;

    @Autowired
    private IWalletService walletService;
    @Autowired
    private WalletRepository walletRepository;

    private static final Gson gson = new Gson();

    @Override
    public ServerResponse sendPaystackAuthorizationUrl(String amount, String currency, String token){

        String email = jwtUtil.getUserEmailFromJWTToken(token);
        BetasaveUser user = betasaveUserRepository.findByEmailAddress(email);
        if(user == null){
            ErrorResponse errorResponse = ErrorResponse.getInstance();
            errorResponse.setResponseCode(ResponseCode.RECORD_NOT_FOUND);
            errorResponse.setResponseMessage(messageProvider.getMessage(ResponseCode.RECORD_NOT_FOUND));
            return  errorResponse;
        }

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer ".concat(Objects.requireNonNull(environment.getProperty("paystack.secretKey"))));
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");

        String transactionReference = OtaUtilities.generateUniqueReference();
        InitTransactionRequestPayload requestPayload = new InitTransactionRequestPayload();
        requestPayload.setAmount(amount);
        requestPayload.setEmail(email);
        requestPayload.setReference(transactionReference);
        requestPayload.setCallbackUrl("http://localhost:8090/wallet/fund/verify");
        requestPayload.setCurrency(currency);
        String requestJson = gson.toJson(requestPayload);

        String url = environment.getProperty("paystack.initializationUrl");
        String response = WebClient.postForObject(url, requestJson, null, headers);
        PaystackWebResponse webResponse = gson.fromJson(response, PaystackWebResponse.class);

        if(webResponse.isConnectionError()){
            ErrorResponse errorResponse = ErrorResponse.getInstance();
            errorResponse.setResponseCode(ResponseCode.THIRD_PARTY_FAILURE);
            errorResponse.setResponseCode(webResponse.getMessage());
            return errorResponse;
        }

        InitTransactionResponsePayload paystackSuccessResponse = gson.fromJson(webResponse.getWebDataString(), InitTransactionResponsePayload.class);
        if(!paystackSuccessResponse.isStatus()){
            ErrorResponse errorResponse = ErrorResponse.getInstance();
            errorResponse.setResponseCode(ResponseCode.THIRD_PARTY_FAILURE);
            errorResponse.setResponseMessage(paystackSuccessResponse.getMessage());
            return errorResponse;
        }

        InitTransactionResponseDTO responseDTO = new InitTransactionResponseDTO();
        responseDTO.setAuthorizationUrl(paystackSuccessResponse.getData().getAuthorizationUrl());

        // Save the transaction to the database.
        TransactionInit transactionInit = new TransactionInit();
        transactionInit.setTransactionReference(transactionReference);
        transactionInit.setInitRequestLog(requestJson);
        transactionInit.setInitResponseLog(gson.toJson(paystackSuccessResponse));
        transactionInit.setCreatedAt(LocalDateTime.now());
        transactionInit.setPaymentGateway(PaymentGateway.PAYSTACK.name());
        transactionInit.setUserEmail(user.getEmailAddress());
        transactionInit.setVerified(false);
        transactionInit.setUpdatedAt(LocalDateTime.now());
        transactionInitRepository.saveAndFlush(transactionInit);

        PayloadResponse payloadResponse = PayloadResponse.getInstance();
        payloadResponse.setResponseCode(ResponseCode.SUCCESS);
        payloadResponse.setResponseMessage(messageProvider.getMessage(ResponseCode.SUCCESS));
        payloadResponse.setResponseData(responseDTO);
        return payloadResponse;
    }

    @Override
    public ServerResponse handlePaystackCallback(Map<String, String> ref) {
        String reference = ref.get("trxref");
        TransactionInit transactionInit = transactionInitRepository.findByTransactionReference(reference);
        String transactionReference = transactionInit.getTransactionReference();

        return verifyInlinePaymentTransaction(transactionReference);
    }

    public ServerResponse verifyInlinePaymentTransaction(String transactionRef) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer ".concat(Objects.requireNonNull(environment.getProperty("paystack.secretKey"))));

        Map<String, Object> params = new HashMap<>();
        params.put("reference", transactionRef);

        String url = environment.getProperty("paystack.verifyTransactionUrl").concat(transactionRef);
        String response = WebClient.getForObject(url,params,headers);
        PaystackWebResponse webResponse = gson.fromJson(response, PaystackWebResponse.class);
        log.info("response: {}", response);
        log.info("web response: {}", webResponse);

        if(webResponse.isConnectionError()){
            ErrorResponse errorResponse = ErrorResponse.getInstance();
            errorResponse.setResponseCode(ResponseCode.THIRD_PARTY_FAILURE);
            errorResponse.setResponseCode(webResponse.getMessage());
            return errorResponse;
        }

        InitTransactionResponsePayload paystackResponse = gson.fromJson(response, InitTransactionResponsePayload.class);
        InitTransactionResponsePayload paystackSuccessResponse = new InitTransactionResponsePayload();
        log.info("paystack response: {}", paystackResponse);
        paystackSuccessResponse.setStatus(webResponse.isStatus());
        paystackSuccessResponse.setMessage(webResponse.getMessage());
        paystackSuccessResponse.setData(paystackResponse.getData());
        log.info("paystack success response: {}", paystackSuccessResponse);
        log.info("web response data string: {}", webResponse.getWebDataString());
        if(!paystackSuccessResponse.isStatus()){
            ErrorResponse errorResponse = ErrorResponse.getInstance();
            errorResponse.setResponseCode(ResponseCode.THIRD_PARTY_FAILURE);
            errorResponse.setResponseMessage(paystackSuccessResponse.getMessage());
            return errorResponse;
        }

        TransactionInit transaction = transactionInitRepository.findByTransactionReference(transactionRef);
        transaction.setVerified(true);
        transactionInitRepository.saveAndFlush(transaction);
        log.info("transaction in: {}", transaction.toString());

        VerifiedTransactions verifiedTransaction = new VerifiedTransactions();
        verifiedTransaction.setId(transaction.getId());
//        verifiedTransaction.setMessage();

        log.info("verified transaction: {}", verifiedTransaction);

        // Credit the user's local wallet.
//        String amount = String.valueOf(verifiedTransaction.getData().getAmount());
        String userEmail = transaction.getUserEmail();
        BetasaveWallet userWallet = walletRepository.findByUserEmail(userEmail);
//        WalletOperationResult creditResult = walletService.processCreditWallet(userWallet, amount);

//        if(creditResult.isHasError()){
//            ErrorResponse errorResponse = ErrorResponse.getInstance();
//            errorResponse.setResponseCode(creditResult.getResponseCode());
//            errorResponse.setResponseMessage(creditResult.getResponseMessage());
//            return errorResponse;
//        }

        PayloadResponse payloadResponse = PayloadResponse.getInstance();
        payloadResponse.setResponseCode(ResponseCode.SUCCESS);
        payloadResponse.setResponseMessage(messageProvider.getMessage(ResponseCode.SUCCESS));
        return payloadResponse;
    }

}
