package com.pentazon.betasave.modules.contribution.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "bt_contribution_user")
public class BetasaveContributionUser {
    @Id
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "contribution_Id")
    private Long contributionId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;
}
