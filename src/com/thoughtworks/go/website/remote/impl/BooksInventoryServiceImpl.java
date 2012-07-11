package com.thoughtworks.go.website.remote.impl;

import com.thoughtworks.go.website.http.HttpClientWrapper;
import com.thoughtworks.go.website.models.StockItem;
import com.thoughtworks.go.website.remote.exceptions.BookNotFoundException;
import com.thoughtworks.go.website.remote.service.BooksInventoryService;
import com.thoughtworks.xstream.XStream;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component
public class BooksInventoryServiceImpl implements BooksInventoryService {

    private Properties properties;
    private HttpClientWrapper httpClientWrapper;

    public BooksInventoryServiceImpl() throws IOException {
        properties = new Properties();
        properties.load(resourceStream());
        httpClientWrapper = new HttpClientWrapper(properties.getProperty("hostname"), Integer.parseInt(properties.getProperty("port")));
    }

    private InputStream resourceStream() throws IOException {
        return this.getClass().getClassLoader().getResource("book-inventory-service.properties").openStream();
    }

    HttpClientWrapper getHttpClientWrapper() {
        return httpClientWrapper;
    }

    public void setHttpWrapper(HttpClientWrapper wrapper) {
        this.httpClientWrapper = wrapper;
    }

    public boolean checkInventory(String isbn) {
        StockItem stockItem;
        try {
            String path = properties.getProperty("path");
            Map<String, String> params = new HashMap<String, String>();
            params.put("isbn", isbn);
            String response = httpClientWrapper.get(path, params);
            XStream xStream = new XStream();
            xStream.alias("stockItem", StockItem.class);
            stockItem = (StockItem) xStream.fromXML(response);
        } catch (Exception e) {
            return false;
        }
        if (stockItem.getBookCount() == -1) {
            throw new BookNotFoundException(String.format("Book '%s' not procured yet", isbn));
        }
        return stockItem.getBookCount() > 0;
    }
}