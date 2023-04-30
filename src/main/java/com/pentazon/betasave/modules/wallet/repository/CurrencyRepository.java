package com.pentazon.betasave.modules.wallet.repository;

import com.pentazon.betasave.modules.wallet.model.BetasaveCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<BetasaveCurrency, Long>
{
    BetasaveCurrency findByCurrencyName(String currencyName);
}
