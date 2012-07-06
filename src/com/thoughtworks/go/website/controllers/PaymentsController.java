package com.thoughtworks.go.website.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PaymentsController {

    @RequestMapping(value = "/payments/index", method = RequestMethod.GET)
    public ModelAndView paymentIndex(@RequestParam("name") String name, HttpServletRequest request) {
        Map model = new HashMap();
        model.put("name", name);
        return new ModelAndView("payments/index", model);
    }

}
