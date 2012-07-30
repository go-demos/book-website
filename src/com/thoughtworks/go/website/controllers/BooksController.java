package com.thoughtworks.go.website.controllers;

import com.thoughtworks.go.website.models.BookCookie;
import com.thoughtworks.go.website.remote.exceptions.BookNotFoundException;
import com.thoughtworks.go.website.remote.service.BooksCollectionService;
import com.thoughtworks.go.website.remote.service.BooksInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class BooksController {

    private BooksCollectionService booksCollectionService;
    private BooksInventoryService booksInventoryService;

    @Autowired
    public BooksController(BooksCollectionService booksCollectionService, BooksInventoryService booksInventoryService) {
        this.booksCollectionService = booksCollectionService;
        this.booksInventoryService = booksInventoryService;
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public ModelAndView allBooks(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        String flash = (String) session.getAttribute("flash");
        session.removeAttribute("flash");
        if (flash != null) {
            model.put("flash_message", flash);
        }
        model.put("books", booksCollectionService.allBooks());
        return new ModelAndView("books/index", model);
    }

    @RequestMapping(value = "/books/book", method = RequestMethod.GET)
    public ModelAndView getBook(@RequestParam("isbn") String isbn,
                                HttpServletRequest request) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("book", booksCollectionService.getBook(isbn));
        boolean exists;
        try {
            exists = booksInventoryService.checkInventory(isbn);
        } catch (BookNotFoundException e) {
            exists = false;
        }
        model.put("available", exists);
        return new ModelAndView("books/book", model);
    }

    @RequestMapping(value = "/books/buy", method = RequestMethod.POST)
    public ModelAndView buyBook(@RequestParam("isbn") String isbn,
                                @RequestParam("name") String name,
                                HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        boolean exists = false;
        try {
            exists = booksInventoryService.checkInventory(isbn);
        } catch (BookNotFoundException e) {
            session.setAttribute("flash", e.getMessage());
            return new ModelAndView(new RedirectView("/books", true), new HashMap());
        }
        if (!exists) {
            session.setAttribute("flash", "Book sold out. Please try again in a couple of days.");
            return new ModelAndView(new RedirectView("/books", true), new HashMap());
        }
        session.setAttribute("current_book", new BookCookie(name, isbn));
        return new ModelAndView(new RedirectView("/payments/index", true), new HashMap());
    }
}
