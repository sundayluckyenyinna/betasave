package com.pentazon.betasave.modules.contribution.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "bs_contribution")
public class BetasaveContribution {
    @Id
    private BigInteger id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "status")
    private String status;

    @Column(name = "name")
    private String name;

    @Column(name = "terminated_at")
    private LocalDateTime terminated_at;

    @Column(name = "termination_due_at")
    private LocalDateTime terminationDueAt;

    @Column(name = "target_amount")
    private BigInteger targetAmount;

    @Column(name = "milestone_amount")
    private BigDecimal milestoneAmount;

    @Column(name = "milestone_percent")
    private BigDecimal milestonePercent;

    @Column(name = "frequency")
    private Number frequency;

    @Column(name = "tenor")
    private Number tenor;

    @Column(name = "creator_id")
    private BigInteger creatorId;

    @Column(name = "invite_link")
    private String inviteLink;
}
