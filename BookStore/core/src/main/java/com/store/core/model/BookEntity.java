package com.store.core.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity who represents the book table.
 *
 * @author rafaelcolombodesouza
 */
@Entity
@Table(name = "book")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class BookEntity extends AbstractEntity {

    @NotNull(message = "The field title is mandatory")
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull(message = "The field author is mandatory")
    @Column(name = "author", nullable = false)
    private String author;

    @Builder
    public BookEntity(Long id, String title, String author) {
        super(id);
        this.title = title;
        this.author = author;
    }
}
