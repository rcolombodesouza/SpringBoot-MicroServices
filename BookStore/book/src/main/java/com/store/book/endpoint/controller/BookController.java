package com.store.book.endpoint.controller;

import com.store.book.endpoint.dto.BookDto;
import com.store.book.endpoint.service.BookService;
import com.store.book.exception.NoResultException;
import com.store.core.model.BookEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Nullable;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;


/**
 * Rest controller to handle requests to book data.
 *
 * @author rafaelcolombodesouza
 */
@RestController
@RequestMapping(value = "v1/admin/book")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "Endpoints to manage book data.")
public class BookController implements IController<BookEntity, BookDto> {

    private final BookService bookService;

    @Override
    @GetMapping(value = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "List all available books.", response = BookEntity[].class)
    public ResponseEntity<Iterable<BookEntity>> findAll(Pageable pageable) {
        return new ResponseEntity<>(bookService.findAll(pageable), OK);
    }

    @Override
    @GetMapping(value = "/find/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "List informed book.", response = BookEntity.class)
    public ResponseEntity<BookEntity> findById(@PathVariable(value = "id") Long id) {
        try {
            BookEntity bookEntity = bookService.findById(id);
            return new ResponseEntity<>(bookEntity, OK);
        } catch (NoResultException e) {
            return new ResponseEntity<>(NO_CONTENT);
        }
    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Save book.", response = BookEntity.class)
    public ResponseEntity<BookEntity> save(BookDto bookDto) {
        return new ResponseEntity<>(bookService.save(bookDto), OK);
    }
}
