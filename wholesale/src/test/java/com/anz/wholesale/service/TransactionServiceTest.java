package com.anz.wholesale.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.anz.wholesale.config.TestConfig;
import com.anz.wholesale.model.Transaction;
import com.anz.wholesale.repository.TransactionRepository;
import com.anz.wholesale.service.impl.TransactionServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

	@InjectMocks
	TransactionServiceImpl transactionService;

	@Mock
	TransactionRepository transactionRepository;

	@Test
	public void shouldReturnNoTransaction() {
		long accountId = 34242231L;
		PageRequest pageReq = PageRequest.of(0, 10);

		when(transactionRepository.findByAccountId(accountId, pageReq))
				.thenReturn(new PageImpl<Transaction>(new ArrayList<Transaction>()));
		Page<Transaction> transactions = transactionService.findTransactions(accountId, pageReq);
		Assert.assertEquals("failure - expected 0 accounts", 0, transactions.getTotalElements());

	}

	@Test
	public void shouldReturnMultipleTransaction() {
		long accountId = 34242231L;
		PageRequest pageReq = PageRequest.of(0, 10);
		int countTransactions = 10;

		when(transactionRepository.findByAccountId(accountId, pageReq))
				.thenReturn(TestConfig.getTransactionPageStubData(accountId, countTransactions));
		Page<Transaction> transactions = transactionService.findTransactions(accountId, pageReq);
		Assert.assertEquals("failure - expected 0 accounts", countTransactions, transactions.getTotalElements());

	}
}
