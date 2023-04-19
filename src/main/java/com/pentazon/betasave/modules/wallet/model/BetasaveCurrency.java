package com.pentazon.betasave.modules.wallet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
@Table(name = "bt_currency")
public class BetasaveCurrency {
    @Id
    private Long id;

    @Column(name = "currency_name")
    private String currencyName;

    @Column(name = "currency_symbol")
    private String currencySymbol;

    @Column(name = "usd_equivalent")
    private BigDecimal usdEquivalent;

    @Column(name = "status")
    private String status;
}
