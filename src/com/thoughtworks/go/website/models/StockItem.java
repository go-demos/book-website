package com.thoughtworks.go.website.models;

public class StockItem {

    private final long id = -1;
    private final String isbn;
    private final int bookCount;

    public StockItem(String isbn, int bookCount) {
        this.isbn = isbn;
        this.bookCount = bookCount;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getBookCount() {
        return bookCount;
    }
}
