package com.thoughtworks.go.website.remote.impl;

import com.thoughtworks.go.website.http.HttpClientWrapper;
import com.thoughtworks.go.website.models.Book;
import com.thoughtworks.go.website.remote.service.BooksCollectionService;
import com.thoughtworks.xstream.XStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class BooksCollectionServiceImpl implements BooksCollectionService {

    private Properties properties;
    private HttpClientWrapper httpClientWrapper;

    public BooksCollectionServiceImpl() throws IOException {
        properties = new Properties();
        properties.load(resourceStream());
        httpClientWrapper = new HttpClientWrapper(properties.getProperty("hostname"), Integer.parseInt(properties.getProperty("port")));
    }

    private InputStream resourceStream() throws IOException {
        return this.getClass().getClassLoader().getResource("book-management-service.properties").openStream();
    }

    public List<Book> allBooks() {
        try {
            String path = properties.getProperty("path");
            String response = httpClientWrapper.get(path);
            XStream xStream = new XStream();
            xStream.alias("book", Book.class);
            return (List) xStream.fromXML(response);
        } catch (Exception e) {
            return new ArrayList<Book>();
        }
    }

    public Book getBook(String isbn) {
        List<Book> books = allBooks();
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) return book;
        }
        throw new RuntimeException(String.format("Book with isbn '%s' not found", isbn));
    }

    HttpClientWrapper getHttpClientWrapper() {
        return httpClientWrapper;
    }

    public void setHttpWrapper(HttpClientWrapper wrapper) {
        this.httpClientWrapper = wrapper;
    }
}