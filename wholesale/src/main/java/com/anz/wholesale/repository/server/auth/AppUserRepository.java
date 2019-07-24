package com.anz.wholesale.repository.server.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anz.wholesale.config.server.auth.AppUser;

/**
 * AppUserRepository for storing the App Users who would be used for authentication
 * @author sumit
 *
 */
public interface AppUserRepository extends JpaRepository<AppUser, String> {

}
