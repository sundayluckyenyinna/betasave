package com.pentazon.betasave.modules.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "bt_savings")
public class BetasaveSavings {
    @Id
    private BigInteger id;

    @Column(name = "user_id")
    private BigInteger userId;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_mobile")
    private String userMobile;

    @Column(name = "goalName")
    private String goalName;

    @Column(name = "status")
    private String status;

    @Column(name = "created_At")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "earliest_termination_date")
    private LocalDateTime earliestTerminationDate;

    @Column(name = "termination_due_date")
    private LocalDateTime terminationDueDate;

    @Column(name = "termination_at")
    private LocalDateTime terminatedAt;

    @Column(name = "target_amount")
    private BigDecimal targetAmount;

    @Column(name = "frequency")
    private Number frequency;

    @Column(name = "tenor")
    private Number tenor;

    @Column(name = "milestone_amount")
    private BigDecimal milestoneAmount;

    @Column(name = "milestone_percent")
    private BigDecimal milestonePercent;

    @Column(name = "is_sms_25_due_send")
    private Boolean isSms25DueSend;

    @Column(name = "is_sms_50_due_send")
    private Boolean isSms50DueSend;

    @Column(name = "is_sms_75_due_send")
    private Boolean isSms75DueSend;

    @Column(name = "total_interest")
    private BigDecimal totalInterest;

    @Column(name = "interest_accrued")
    private BigDecimal interestAccrued;

    @Column(name = "saving_interest_id")
    private BigInteger savingInterestId;
}
