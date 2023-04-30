package com.pentazon.betasave.modules.wallet.repository;

import com.pentazon.betasave.modules.wallet.model.BetasaveWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<BetasaveWallet, Long>
{

}
