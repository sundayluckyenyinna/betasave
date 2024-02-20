package com.pentazon.betasave.modules.transactions.repository;

import com.pentazon.betasave.modules.transactions.model.TransactionInit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionInitRepository extends JpaRepository<TransactionInit, Long>
{
    TransactionInit findByUserEmail(String emailAddress);
    TransactionInit findByTransactionReference(String ref);
}
