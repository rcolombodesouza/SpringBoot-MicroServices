package com.store.book.endpoint.service;

import com.store.book.endpoint.dto.AbstractDto;
import com.store.book.exception.NoResultException;
import com.store.core.model.IEntity;
import org.springframework.data.domain.Pageable;

/**
 * Interface to handle requests to/from database.
 * @param <T> Entity which must implement IEntity interface.
 *
 * @author rafaelcolombodesouza
 */
public interface IService<T extends IEntity, D extends AbstractDto> {

    /**
     * Get all books from the database.
     * @param pageable the results will be pageable.
     * @return pageable book list.
     */
    Iterable<T> findAll (Pageable pageable);

    /**
     * Get the book based on the informed id.
     * @param id book id.
     * @return the book.
     */
    T findById(Long id) throws NoResultException;

    /**
     * Save the book into the database.
     * @param dto to be converted to an Entity and saved to the database.
     * @return the saved entity.
     */
    T save(D dto);
}