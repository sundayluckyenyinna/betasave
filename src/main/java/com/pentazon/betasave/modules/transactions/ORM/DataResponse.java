package com.pentazon.betasave.modules.transactions.ORM;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class DataResponse {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String domain;

        private String status;

        private String reference;

        private Long amount;

        private String message;

        private String gatewayResponse;

        private String paidAt;

        private String createdAt;

        private String channel;

        private String currency;

        private String ipAddress;
}
