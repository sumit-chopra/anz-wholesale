package com.anz.wholesale.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.anz.wholesale.model.Transaction;
import com.anz.wholesale.repository.TransactionRepository;
import com.anz.wholesale.service.TransactionService;

/**
 * The TransactionServiceImpl encapsulates all business behaviours operating on the Transaction entity model
 * @author sumit
 *
 */
@Service
public class TransactionServiceImpl implements TransactionService {

	private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
	
	/**
	 * The Spring Data repository for Transaction entities
	 */
	@Autowired
	private transient TransactionRepository txnRepositry;
	
	@Override
	public Page<Transaction> findTransactions(long accountId, Pageable pageable) {
		logger.info("> findTransactions");
		
		Page<Transaction> txns = txnRepositry.findByAccountId(accountId, pageable);
		
		logger.info("< findTransactions");
		return txns;
	}
}
