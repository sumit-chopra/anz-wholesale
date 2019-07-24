package com.anz.wholesale.config.server.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * This is an entity class for holding the authenticated user object.
 * This is primarily a part of authorization server and not in scope of this implementation.
 * @author sumit
 *
 */

@Entity
@Table(name = "app_user")
@Getter @Setter
public class AppUser {

    @Id
    @Column(length = 50)
    private String userId;
    
    @Column(length = 100)
    private String userPass;

    @Column(length = 50)
    private String userRole;
    
}
