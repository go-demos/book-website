package com.thoughtworks.go.website.remote.impl;

import com.thoughtworks.go.website.http.HttpClientWrapper;
import com.thoughtworks.go.website.models.Book;
import com.thoughtworks.go.website.remote.service.BooksCollectionService;
import com.thoughtworks.xstream.XStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

@Component
public class BooksCollectionServiceImpl implements BooksCollectionService {

    private Properties properties;

    public BooksCollectionServiceImpl() throws IOException {
        URL resource = this.getClass().getClassLoader().getResource("/book-management-service.properties");
        properties = new Properties();
        properties.load(resource.openStream());
    }

    public List<Book> allBooks() {
        String hostname = properties.getProperty("hostname");
        int port = Integer.parseInt(properties.getProperty("port"));
        String path = properties.getProperty("path");
        String response = new HttpClientWrapper(hostname, port).get(path);
        XStream xStream = new XStream();
        xStream.alias("book", Book.class);
        return (List) xStream.fromXML(response);
    }
}
