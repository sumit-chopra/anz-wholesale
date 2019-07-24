package com.anz.wholesale.service;

import java.util.List;
import java.util.Optional;

import com.anz.wholesale.model.Account;


/**
 * <p>
 * The AccountService interface defines all public business behaviors for operations on the Account entity model.
 * </p>
 * <p>
 * This interface should be injected into AccountService clients, not the implementation.
 * </p>
 * 
 * @author Sumit Chopra
 */
public interface AccountService {

	/**
	 * Finds all the accounts belong to the given user
	 * 
	 * @param userId - Id of the user whose accounts are being looked up
	 * @return A list of accounts
	 */
	List<Account> findAccounts(String userId);
	
	Optional<Account> findOne(long accountId);
	
	public void evitCache();
	
}
