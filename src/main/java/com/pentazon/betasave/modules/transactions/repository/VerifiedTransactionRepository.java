package com.pentazon.betasave.modules.transactions.repository;

import com.pentazon.betasave.modules.transactions.model.VerifiedTransactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerifiedTransactionRepository extends JpaRepository<VerifiedTransactions, Long> {

}
