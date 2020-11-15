package com.store.book.endpoint.dto;

public class BookDto extends AbstractDto {

    private String title;
    private String author;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "BookDto{" + "id='" + super.getId() + '\'' +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
