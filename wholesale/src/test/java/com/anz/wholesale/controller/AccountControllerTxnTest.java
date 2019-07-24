package com.anz.wholesale.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.anz.wholesale.config.TestConfig;
import com.anz.wholesale.model.Transaction;
import com.anz.wholesale.service.AccountService;
import com.anz.wholesale.service.TransactionService;
import com.anz.wholesale.util.StringUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AccountControllerTxnTest {

	private static final String TXN_URI = "/api/v1/accounts/{id}/transactions";

	private static final long ACCOUNT_ID = 3822827392L;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private transient AccountService accountService;

	@MockBean
	private transient TransactionService transactionService;

	@Test
	public void shouldRejectIfNoAuthentication() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(TXN_URI, ACCOUNT_ID).accept(MediaType.ALL))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = "user")
	public void shouldRejectIfAccountNotFound() throws Exception {

		when(accountService.findOne(ACCOUNT_ID)).thenReturn(Optional.empty());

		mockMvc.perform(MockMvcRequestBuilders.get(TXN_URI, ACCOUNT_ID).accept(MediaType.ALL))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "user")
	public void shouldRejectIfDifferentUser() throws Exception {
		String userId = "user";
		when(accountService.findOne(ACCOUNT_ID)).thenReturn(TestConfig.getAccountEntityStubData(ACCOUNT_ID, "user1"));

		mockMvc.perform(MockMvcRequestBuilders.get(TXN_URI, ACCOUNT_ID).accept(MediaType.ALL))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "user")
	public void shouldReturnZeroTransactions() throws Exception {
		String userId = "user";
		when(accountService.findOne(ACCOUNT_ID)).thenReturn(TestConfig.getAccountEntityStubData(ACCOUNT_ID, userId));

		when(transactionService.findTransactions(ACCOUNT_ID, PageRequest.of(0, 10)))
				.thenReturn(new PageImpl<Transaction>(new ArrayList<Transaction>()));
		ResultActions result = mockMvc
				.perform(MockMvcRequestBuilders.get(TXN_URI, ACCOUNT_ID).param("page", "0").param("size", "10")
						.accept(MediaType.ALL))
				.andExpect(status().isOk()).andExpect(jsonPath("$.totalElements", is(0)));

		// Verify the AccountService.findAccounts method was invoked once
		verify(accountService, times(1)).findOne(ACCOUNT_ID);
		verify(transactionService, times(1)).findTransactions(ACCOUNT_ID, PageRequest.of(0, 10));

		String resultString = result.andReturn().getResponse().getContentAsString();
		System.out.println("_________________________" + resultString);
		Assert.assertTrue("failure - expected HTTP response body to have a value",
				!StringUtil.isNullOrEmpty(resultString));
	}

	@Test
	@WithMockUser(username = "user")
	public void shouldReturnMultipleTransactions() throws Exception {
		String userId = "user";
		int txnCount = 10;
		when(accountService.findOne(ACCOUNT_ID)).thenReturn(TestConfig.getAccountEntityStubData(ACCOUNT_ID, userId));

		when(transactionService.findTransactions(ACCOUNT_ID, PageRequest.of(0, 10)))
				.thenReturn(TestConfig.getTransactionPageStubData(ACCOUNT_ID, txnCount));
		ResultActions result = mockMvc
				.perform(MockMvcRequestBuilders.get(TXN_URI, ACCOUNT_ID).param("page", "0").param("size", "10")
						.accept(MediaType.ALL))
				.andExpect(status().isOk()).andExpect(jsonPath("$.totalElements", is(10)));

		// Verify the AccountService.findAccounts method was invoked once
		verify(accountService, times(1)).findOne(ACCOUNT_ID);
		verify(transactionService, times(1)).findTransactions(ACCOUNT_ID, PageRequest.of(0, 10));

		String resultString = result.andReturn().getResponse().getContentAsString();
		Assert.assertTrue("failure - expected HTTP response body to have a value",
				!StringUtil.isNullOrEmpty(resultString));
		System.out.println("_________________________" + resultString);
	}

}