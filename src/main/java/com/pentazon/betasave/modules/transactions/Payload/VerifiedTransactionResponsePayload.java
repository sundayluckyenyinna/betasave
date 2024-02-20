package com.pentazon.betasave.modules.transactions.Payload;

import com.pentazon.betasave.modules.transactions.ORM.DataResponse;
import lombok.Data;

@Data
public class VerifiedTransactionResponsePayload {
        private boolean status;

        private String message;

        private DataResponse data;
}
