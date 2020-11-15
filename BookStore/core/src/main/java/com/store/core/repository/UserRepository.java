package com.store.core.repository;

import com.store.core.model.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Responsible to access the User table.
 *
 * @author rafaelcolombodesouza
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

    /**
     * Return the user register based on the given username.
     * @param username parameter to get the User register.
     * @return the UserEntity register.
     */
    UserEntity findByUsername(String username);
}
