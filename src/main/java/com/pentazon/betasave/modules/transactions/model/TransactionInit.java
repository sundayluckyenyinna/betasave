package com.pentazon.betasave.modules.transactions.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Entity
@Table(name = "bs_transaction_init")
public class TransactionInit
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "payment_gateway")
    private String paymentGateway;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_id")
    public String userId;

    @Column(name = "transaction_reference")
    private String transactionReference;

    @Column(name = "init_request_log")
    private String initRequestLog;

    @Column(name = "init_response_log")
    private String initResponseLog;

    @Column(name = "transaction_status")
    private boolean isVerified;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
