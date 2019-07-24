package com.anz.wholesale.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Account class is an entity model object.
 * @author sumit
 *
 */

@Entity
@Getter @Setter @NoArgsConstructor
public class Account implements Serializable {
	
	public enum AccountType {
		
		CURRENT,
		SAVINGS

	}

    private static final long serialVersionUID = 1L;

    /**
     * The account number
     */
    @Id
    private Long id;
    
    /**
     * Name of the account
     */
    private String accountName;
    
    /**
     * Type of account either - Savings or Current
     */
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    
    /**
     * Opening balance of the account
     */
    private double openingBalance;
    
    /**
     * Date on which the opening balance was recorded
     */
    private Instant balanceDate;
    
    /**
     * Id of the user to whom this account belongs
     */
    private String userId;
    
    /**
     * Currency in which account holder holds the balance
     */
    @Enumerated(EnumType.STRING)
    private Currency currency;
	
}
