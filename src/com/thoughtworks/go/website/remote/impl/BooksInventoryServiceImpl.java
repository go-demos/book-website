package com.thoughtworks.go.website.remote.impl;

import com.thoughtworks.go.website.remote.service.BooksInventoryService;
import org.springframework.stereotype.Component;

@Component
public class BooksInventoryServiceImpl implements BooksInventoryService {
    public boolean checkInventory(String isbn) {
        return false;
    }
}
