package com.anz.wholesale.model;

import java.time.Instant;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Index;

import org.springframework.stereotype.Indexed;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Transaction class is an Entity model object.
 * @author sumit
 *
 */

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "Transaction",
indexes = {@Index(name = "account_id_idx",  columnList="account_id", unique = false)})
public class Transaction {

	public enum TransactionType {
		CREDIT,
		DEBIT
	}
	
	/**
	 * Id for the transaction
	 */
	@Id
	private Long id;
	
	/**
	 * Account used for this transaction
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id")
	private Account account;
	
	/**
	 * Amount of the transaction
	 */
	private double amount;
	
	/**
	 * Type of the transaction - CREDIT or DEBIT
	 */
	
	@Enumerated(EnumType.STRING)
	private TransactionType txnType;
	
	/**
	 * Currency in which the transaction was carried out.
	 */
	@Enumerated(EnumType.STRING)
	private Currency currency;
	
	/**
	 * Time and date of the transaction
	 */
	private Instant txnDate;
	
}
