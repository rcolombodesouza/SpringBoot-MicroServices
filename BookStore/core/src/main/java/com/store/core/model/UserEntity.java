package com.store.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity who represents the user table.
 *
 * @author rafaelcolombodesouza
 */
@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserEntity extends AbstractEntity {

    @NotNull(message = "The field 'username' is mandatory.")
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull(message = "The field 'password' is mandatory.")
    @Column(name = "password", nullable = false)
    @ToString.Exclude
    private String password;

    @NotNull(message = "The field 'role' is mandatory.")
    @Column(name = "role", nullable = false)
    private String role = "USER";

    @Builder
    public UserEntity(Long id, String username, String password, String role) {
        super(id);
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public UserEntity(@NotNull UserEntity userEntity) {
        super(userEntity.getId());
        this.username = userEntity.getUsername();
        this.password = userEntity.getPassword();
        this.role = userEntity.getRole();
    }

}
