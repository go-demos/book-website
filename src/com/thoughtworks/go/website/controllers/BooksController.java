package com.thoughtworks.go.website.controllers;

import com.thoughtworks.go.website.models.BookCookie;
import com.thoughtworks.go.website.remote.service.BooksCollectionService;
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

    @Autowired
    public BooksController(BooksCollectionService booksCollectionService) {
        this.booksCollectionService = booksCollectionService;
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public ModelAndView allBooks() {
        Map model = new HashMap();
        model.put("books", booksCollectionService.allBooks());
        return new ModelAndView("books/index", model);
    }

    @RequestMapping(value = "/books/buy", method = RequestMethod.POST)
    public ModelAndView buyBook(@RequestParam("isbn") String isbn,
                                @RequestParam("name") String name,
                                HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.setAttribute("current_book", new BookCookie(name, isbn));
        return new ModelAndView(new RedirectView("/payments/index", true), new HashMap());
    }

}
