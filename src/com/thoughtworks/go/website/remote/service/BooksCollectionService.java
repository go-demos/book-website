package com.thoughtworks.go.website.remote.service;

import com.thoughtworks.go.website.models.Book;

import java.io.IOException;
import java.util.List;

public interface BooksCollectionService {

    List<Book> allBooks();

    Book getBook(String isbn);
}
