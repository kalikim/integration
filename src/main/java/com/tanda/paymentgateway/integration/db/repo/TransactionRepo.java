package com.tanda.paymentgateway.integration.db.repo;


import com.tanda.paymentgateway.integration.db.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    Transaction getTransactionByConversationID(String conversationID);
}
