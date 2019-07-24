package com.anz.wholesale.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.anz.wholesale.model.Account;
import com.anz.wholesale.repository.AccountRepository;
import com.anz.wholesale.service.AccountService;

/**
 * The AccountServiceImpl encapsulates all business behaviours operating on the Account entity model
 * @author sumit
 *
 */
@Service
public class AccountServiceImpl implements AccountService {

	private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	/**
	 * The Spring Data repository for Account entities
	 */
	@Autowired
	private transient AccountRepository accountRepositry;
	
	
	/**
	 * Find accounts for the given user.
	 * Cache the result from DB
	 * 
	 * @param userId Id of the user whose accounts need to be fetched
	 * @return A list of account objects
	 */
	@Override
	@Cacheable(value="accounts_per_user", key="#userId")
	public List<Account> findAccounts(String userId) {
		logger.info("> findAccounts");
		
		List<Account> accounts = accountRepositry.findByUserId(userId);
		
		logger.info("< findAccounts");
		return accounts;
	}
	
	/**
	 * Find specific account by the account id
	 * Cache the result from DB
	 * 
	 * @param accountId Id of the account to be fetched
	 * @return Optional Account object
	 */
	@Override
	@Cacheable(value="account", key="#accountId")
	public Optional<Account> findOne(long accountId) {
		logger.info("> findOne");
		
		final Optional<Account> accountOptional = accountRepositry.findById(accountId);
		
		logger.info("< findOne");
		return accountOptional;
	}
	
	/**
	 * Deletes the cache
	 */
	@Override
	@CacheEvict(value= {"accounts", "accounts_per_user"}, allEntries = true)
	public void evitCache() {
		logger.info("> evictCache");
		logger.info("< evictCache");
	}
}
