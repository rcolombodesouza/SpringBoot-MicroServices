package com.store.core.model;

import java.io.Serializable;

/**
 * Interface to handler all entities from the database
 *
 * @author rafaelcolombodesouza
 */
public interface IEntity extends Serializable {

    /**
     * @return the id from the entity
     */
    Long getId();
}
