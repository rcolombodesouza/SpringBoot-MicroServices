package com.store.book.endpoint.controller;

import com.store.book.endpoint.dto.AbstractDto;
import com.store.core.model.IEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

/**
 * Interface for the rest controllers with default functions to be implemented.
 * @param <T> T is an Entity object which implements IEntity.
 * @param <D> d is the abstract DTO.
 * @author rafaelcolombodesouza
 */
public interface IController<T extends IEntity, D extends AbstractDto> {

    /**
     * Retrieve all books from the database.
     * @param pageable the results will be pageable.
     * @return the ReponseEntity object containing the list of books.
     */
    ResponseEntity<Iterable<T>> findAll(Pageable pageable);

    /**
     * Retrieve the object by it's title.
     * @param id object id.
     * @return the book.
     */
    ResponseEntity<T> findById(Long id);

    /**
     * Save informed book
     * @param dto data to be saved
     * @return
     */
    ResponseEntity<T> save(D dto);
}
