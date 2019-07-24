package com.anz.wholesale.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.anz.wholesale.config.TestConfig;
import com.anz.wholesale.model.Account;
import com.anz.wholesale.service.AccountService;
import com.anz.wholesale.util.StringUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AccountControllerTest {

	private static final String ACCOUNT_URL = "/api/v1/accounts";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private transient AccountService accountService;

	@Test
	public void shouldRejectIfNoAuthentication() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(ACCOUNT_URL).accept(MediaType.ALL))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = "user")
	public void shouldAllowAnyAuthenticatedUserAccount() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(ACCOUNT_URL).accept(MediaType.ALL)).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "user")
	public void shouldReturnZeroAccounts() throws Exception {
		String userId = "user";
		when(accountService.findAccounts(userId)).thenReturn(new ArrayList<Account>());

		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(ACCOUNT_URL).accept(MediaType.ALL))
				.andExpect(status().isOk());

		// Verify the AccountService.findAccounts method was invoked once
		verify(accountService, times(1)).findAccounts(userId);

		String resultString = result.andReturn().getResponse().getContentAsString();
		Assert.assertTrue("failure - expected HTTP response body to have a value",
				!StringUtil.isNullOrEmpty(resultString));

		JacksonJsonParser jsonParser = new JacksonJsonParser();
		int accountListSize = jsonParser.parseList(resultString).size();

		Assert.assertEquals("failure - expected no accounts", 0, accountListSize);
	}

	@Test
	@WithMockUser(username = "user")
	public void shouldReturnMultipleAccounts() throws Exception {
		String userId = "user";
		int countOfAccounts = 10;
		when(accountService.findAccounts(userId))
				.thenReturn(TestConfig.getAccountEntityListStubData(userId, countOfAccounts));

		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(ACCOUNT_URL).accept(MediaType.ALL))
				.andExpect(status().isOk());

		// Verify the AccountService.findAccounts method was invoked once
		verify(accountService, times(1)).findAccounts(userId);

		String resultString = result.andReturn().getResponse().getContentAsString();
		Assert.assertTrue("failure - expected HTTP response body to have a value",
				!StringUtil.isNullOrEmpty(resultString));

		JacksonJsonParser jsonParser = new JacksonJsonParser();
		int accountListSize = jsonParser.parseList(resultString).size();

		Assert.assertEquals("failure - expected no accounts", countOfAccounts, accountListSize);
	}
}