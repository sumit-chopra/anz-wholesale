package com.anz.wholesale.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anz.wholesale.model.Transaction;


/**
 * <p>
 * The TransactionService interface defines all public business behaviors for operations on the Transaction entity model.
 * </p>
 * <p>
 * This interface should be injected into TransactionService clients, not the implementation.
 * </p>
 * 
 * @author Sumit Chopra
 */
public interface TransactionService {

	/**
	 * Finds all the accounts belong to the given user
	 * 
	 * @param userId - Id of the user whose accounts are being looked up
	 * @return A list of accounts
	 */
	Page<Transaction> findTransactions(long accountId, Pageable pageable);
	
}
