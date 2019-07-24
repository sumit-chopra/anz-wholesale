package com.anz.wholesale.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.anz.wholesale.model.Account;
import com.anz.wholesale.model.Currency;
import com.anz.wholesale.model.Transaction;
import com.anz.wholesale.model.Account.AccountType;
import com.anz.wholesale.model.Transaction.TransactionType;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        User basicUser1 = new org.springframework.security.core.userdetails.User(
                "user1",
                "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        User basicUser2 = new org.springframework.security.core.userdetails.User(
                "user2",
                "password",
                Collections.emptyList());

        return new InMemoryUserDetailsManager(basicUser1, basicUser2);
    }
    
    
    public static List<Account> getAccountEntityListStubData(String userId, int count) {
		final List<Account> list = new ArrayList<Account>();
		for (int i = 0; i < count; i++)
			list.add(getAccountEntityStubData(userId));
		return list;
	}

	public static Account getAccountEntityStubData(String userId) {
		final Account entity = new Account();
		entity.setAccountName("SGSavings26");
		entity.setId(new Random().nextLong());
		entity.setAccountType(AccountType.SAVINGS);
		entity.setBalanceDate(Instant.now());
		entity.setOpeningBalance(84327.51);
		entity.setUserId(userId);
		return entity;
	}

	public static Optional<Account> getAccountEntityStubData(long accountId, String userId) {
		final Account entity = new Account();
		entity.setAccountName("SGSavings26");
		entity.setId(accountId);
		entity.setAccountType(AccountType.SAVINGS);
		entity.setBalanceDate(Instant.now());
		entity.setOpeningBalance(84327.51);
		entity.setUserId(userId);

		return Optional.of(entity);

	}
    
    public static Page<Transaction> getTransactionPageStubData(long accountId, int count) {
		List<Transaction> list = getTransactionEntityListStubData(accountId, count);
		Page<Transaction> page = new PageImpl<Transaction>(list);
		return page;
	}

	private static List<Transaction> getTransactionEntityListStubData(long accountId, int count) {
		final List<Transaction> list = new ArrayList<Transaction>();
		for (int i = 0; i < count; i++)
			list.add(getTransactionEntityStubData(accountId));
		return list;
	}

	public static Transaction getTransactionEntityStubData(long accountId) {
		final Transaction entity = new Transaction();

		final Account accEntity = new Account();
		accEntity.setAccountName("SGSavings26");
		accEntity.setId(accountId);
		accEntity.setAccountType(AccountType.SAVINGS);
		accEntity.setBalanceDate(Instant.now());
		accEntity.setOpeningBalance(84327.51);
		accEntity.setCurrency(Currency.SGD);
		accEntity.setUserId("user1");

		entity.setAccount(accEntity);
		entity.setAmount(1000);
		entity.setCurrency(Currency.SGD);
		entity.setId(new Random().nextLong());
		entity.setTxnDate(Instant.now());
		entity.setTxnType(TransactionType.CREDIT);

		return entity;
	}
}