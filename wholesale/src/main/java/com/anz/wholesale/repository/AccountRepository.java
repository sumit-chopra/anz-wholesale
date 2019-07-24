package com.anz.wholesale.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anz.wholesale.model.Account;

/**
 * The AccountRepository interface is a Spring Data JPA repository for
 * Account entities. The AccountRepository provides all the data access
 * behavious exposed by JPARepository. 
 * @author sumit
 *
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	
	List<Account> findByUserId(String userId);

}
