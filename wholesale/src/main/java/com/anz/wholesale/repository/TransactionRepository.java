package com.anz.wholesale.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anz.wholesale.model.Transaction;

/**
 * The AccountRepository interface is a Spring Data JPA repository for
 * Account entities. The AccountRepository provides all the data access
 * behavious exposed by JPARepository. 
 * @author sumit
 *
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	Page<Transaction> findByAccountId(long accountId, Pageable page);

}
