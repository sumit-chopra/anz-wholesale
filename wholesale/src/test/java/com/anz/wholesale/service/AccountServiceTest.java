package com.anz.wholesale.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.anz.wholesale.config.TestConfig;
import com.anz.wholesale.model.Account;
import com.anz.wholesale.repository.AccountRepository;
import com.anz.wholesale.service.impl.AccountServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

	@InjectMocks
	AccountServiceImpl accountService;

	@Mock
	AccountRepository accountRepository;

	@Test
	public void shouldReturnNoAccountByUserId() {
		String userId = "user";

		when(accountRepository.findByUserId(userId)).thenReturn(new ArrayList<Account>());
		List<Account> accounts = accountService.findAccounts(userId);
		Assert.assertEquals("failure - expected 0 accounts", 0, accounts.size());

	}

	@Test
	public void shouldReturnMultipleAccounts() {
		String userId = "user";

		when(accountRepository.findByUserId(userId)).thenReturn(TestConfig.getAccountEntityListStubData(userId, 10));
		List<Account> accounts = accountService.findAccounts(userId);
		Assert.assertEquals("failure - expected 10 accounts", 10, accounts.size());

	}

	@Test
	public void shouldReturnNoAccount() {
		long accountId = 3432292L;

		when(accountRepository.findById(accountId)).thenReturn(Optional.empty());
		Optional<Account> optionalAccount = accountService.findOne(accountId);
		Assert.assertEquals("failure - expected no account", true, optionalAccount.isEmpty());
	}

	@Test
	public void shouldReturnAccount() {
		long accountId = 3432292L;
		String userId = "user";

		when(accountRepository.findById(accountId)).thenReturn(TestConfig.getAccountEntityStubData(accountId, userId));
		Optional<Account> optionalAccount = accountService.findOne(accountId);
		Assert.assertEquals("failure - expected an account", false, optionalAccount.isEmpty());
		Assert.assertEquals("failure - expected an account", accountId, optionalAccount.get().getId().longValue());
	}

}
