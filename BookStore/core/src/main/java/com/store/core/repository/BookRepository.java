package com.store.core.repository;

import com.store.core.model.BookEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Responsible to access the Book table.
 *
 * @author rafaelcolombodesouza
 */
@Repository
public interface BookRepository extends PagingAndSortingRepository<BookEntity, Long> {
}
