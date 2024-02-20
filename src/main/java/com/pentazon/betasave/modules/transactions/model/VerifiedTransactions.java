package com.pentazon.betasave.modules.transactions.model;

import com.pentazon.betasave.modules.transactions.ORM.DataResponse;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Getter
@Setter
@Entity
//@Table(name = "bs_transaction_verified")
public class VerifiedTransactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "status")
    private boolean status;

    @Column(name = "message")
    private String message;
    
    private String data;
}