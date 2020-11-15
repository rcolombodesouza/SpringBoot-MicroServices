package com.store.book.endpoint.service;

import com.store.book.endpoint.dto.BookDto;
import com.store.book.exception.NoResultException;
import com.store.core.model.BookEntity;
import com.store.core.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class to handler user data retrieved from the database.
 *
 * @author rafaelcolombodesouza
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class BookService implements IService<BookEntity, BookDto> {

    private final BookRepository bookRepository;

    @Override
    public Iterable<BookEntity> findAll(Pageable pageable) {
        log.info("Listing all books.");
        return bookRepository.findAll(pageable);
    }

    @Override
    public BookEntity findById(Long id) throws NoResultException {
        log.info(String.format("Searching book %s.", id));
        Optional<BookEntity> optionalBookEntity = bookRepository.findById(id);
        if(optionalBookEntity.isEmpty()) {
            throw new NoResultException(String.format("Book with id %d was not found.", id));
        }
        return optionalBookEntity.get();
    }

    @Override
    public BookEntity save(BookDto dto) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(dto.getId());
        bookEntity.setTitle(dto.getTitle());
        bookEntity.setAuthor(dto.getAuthor());
        return bookRepository.save(bookEntity);
    }
}
