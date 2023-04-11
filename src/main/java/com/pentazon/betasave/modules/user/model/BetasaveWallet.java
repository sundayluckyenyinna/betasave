package com.pentazon.betasave.modules.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "bt_wallet")
public class BetasaveWallet {
    @Id
    private BigInteger id;

    @Column(name = "wallet_id")
    private BigInteger walletId;

    @Column(name = "user_id")
    private BigInteger userId;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "created_at")
    private LocalDateTime createAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "currency_id")
    private BigInteger currencyId;

    @Column(name = "balance")
    private BigDecimal balance;
}
