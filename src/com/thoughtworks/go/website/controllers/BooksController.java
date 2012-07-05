package com.thoughtworks.go.website.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class BooksController {

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public ModelAndView allBooks(HttpServletRequest request) {
        return new ModelAndView("books/index", new HashMap());
    }

    @RequestMapping(value = "/books/create", method = RequestMethod.POST)
    public ModelAndView createBook(@RequestParam("isbn") String isbn,
                                   @RequestParam("name") String name,
                                   @RequestParam("author") String author,
                                   @RequestParam("publisher") String publisher,
                                   HttpServletRequest request) {
        return new ModelAndView(new RedirectView("/books", true));
    }
}
