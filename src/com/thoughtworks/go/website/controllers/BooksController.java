package com.thoughtworks.go.website.controllers;

import com.thoughtworks.go.website.remote.service.BooksCollectionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class BooksController {

    private BooksCollectionService booksCollectionService;

    public BooksController(BooksCollectionService booksCollectionService) {
        this.booksCollectionService = booksCollectionService;
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public ModelAndView allBooks(HttpServletRequest request) {
        Map model = new HashMap();
        model.put("books", booksCollectionService.allBooks());
        return new ModelAndView("books/index", model);
    }
}
