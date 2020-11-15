package com.store.authentication.security.user;

import com.store.core.model.UserEntity;
import com.store.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * Service class to manager user details.
 *
 * @author rafaelcolombodesouza
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Search the user according to the informed username.
     * @param userName data to search in the database.
     * @return an UserDetails object.
     */
    @Override
    public UserDetails loadUserByUsername(String userName) {
        log.info(String.format("Searching in the DB the user by username %s.", userName));
        UserEntity userEntity = userRepository.findByUsername(userName);
        if(userEntity == null) {
            throw new UsernameNotFoundException(String.format("UserEntity %s not found.", userName));
        }
        log.info(String.format("UserEntity found %s.", userEntity.toString()));
        return new CustomUserDetails(userEntity);
    }

    private static final class CustomUserDetails extends UserEntity implements UserDetails {

        CustomUserDetails(@NotNull UserEntity userEntity) {
            super(userEntity);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_" + this.getRole());
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
