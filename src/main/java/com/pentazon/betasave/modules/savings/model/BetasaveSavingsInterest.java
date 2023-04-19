package com.pentazon.betasave.modules.savings.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "bt_saving_interest")
public class BetasaveSavingsInterest {
    @Id
    private Long Id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_mobile")
    private String userMobile;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "interest_amount")
    private BigDecimal interestAmount;

    @Column(name = "interest_accrued_percent")
    private BigDecimal interestAccruedPercent;

    @Column(name = "savings_id")
    private Long savingsId;
}
