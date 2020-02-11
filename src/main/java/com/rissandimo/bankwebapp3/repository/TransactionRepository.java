package com.rissandimo.bankwebapp3.repository;

import com.rissandimo.bankwebapp3.dao.Transaction;
import com.rissandimo.bankwebapp3.dao.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Integer>
{
    List<Transaction> findTransactionsByUser(User user);
}
