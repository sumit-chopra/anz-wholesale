package com.anz.wholesale.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anz.wholesale.exception.AccountNotFoundException;
import com.anz.wholesale.exception.ForbiddenException;
import com.anz.wholesale.model.Account;
import com.anz.wholesale.model.Transaction;
import com.anz.wholesale.service.AccountService;
import com.anz.wholesale.service.TransactionService;

/**
 * The AccountController class is a RESTful web service controller.
 * 
 * @author sumit
 *
 */
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

	/**
	 * The Logger for this Class.
	 */
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private transient AccountService accountService;

	@Autowired
	private transient TransactionService txnService;

	/**
	 * Web Service endpoint to fetch all the accounts of the user. The service
	 * returns the collection of account entities as JSON
	 * 
	 * @param principal The logged in user
	 * @return A list of account objects
	 */
	@GetMapping
	@Secured({ "ROLE_USER" })
	public List<Account> getAccounts(Principal principal) {
		logger.info("> getAccounts " + principal.getName());

		List<Account> accounts = new ArrayList<Account>();
		String userId = principal.getName();
		accounts = accountService.findAccounts(userId);

		logger.info("< getAccounts");
		return accounts;
	}

	
	/**
	 * Web Service endpoint to fetch all the transaction for the given account id. The service
	 * returns the collection of transaction entities as JSON
	 * 
	 * @param accountId Account id for which transactions to be fetched
	 * @param pageable Pageable param indicating page number and size - denoting number of results in the slice
	 * @param principal The logged in user
	 * @return A list of transaction objects. Object describing the page as well
	 */
	@GetMapping("/{accountId}/transactions")
	public Page<Transaction> getTransactions(@PathVariable final long accountId, Pageable pageable,
			Principal principal) {
		logger.info("> getTransactions " + accountId + " for user " + principal.getName());

		Optional<Account> accountOptional = accountService.findOne(accountId);
		if (accountOptional.isEmpty()) {
			logger.error("Account " + accountId + " could not be found");
			throw new AccountNotFoundException("Account not found");
		}
		if (!principal.getName().equalsIgnoreCase(accountOptional.get().getUserId())) {
			logger.error("Account " + accountId + " does not belong to user " + principal.getName());
			throw new ForbiddenException("Sorry, you are not authorized to fetch transactions for this account");
		}

		final Page<Transaction> transactions = txnService.findTransactions(accountId, pageable);

		logger.info("< getTransactions");
		return transactions;
	}
	
	/**
	 * Web Service endpoint to delete the cache. This is a dummy API for now.
	 * In the real application, this function will be called up from the appropriate places.
	 * @return
	 */
	@GetMapping("/deletecache")
	public String deleteCache() {
		logger.info("> deleteCache");

		
		accountService.evitCache();

		logger.info("< deleteCache");
		return "DONE";
	}
}
