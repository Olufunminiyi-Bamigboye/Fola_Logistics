package com.wayup.Fola_Logistics.repository;

import com.wayup.Fola_Logistics.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
//    List<Transaction> findByStatus(Transaction.PaymentStatus status);
}
