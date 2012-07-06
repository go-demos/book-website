package com.thoughtworks.go.website.controllers;

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
public class PaymentsController {

    @RequestMapping(value = "/payments/index", method = RequestMethod.GET)
    public ModelAndView paymentIndex(HttpServletRequest request) {
        Map model = new HashMap();
        model.put("book", request.getSession().getAttribute("current_book"));
        return new ModelAndView("payments/index", model);
    }

    @RequestMapping(value = "/payments/success", method = RequestMethod.GET)
    public ModelAndView paymentSuccessful(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map model = new HashMap();
        model.put("book", session.getAttribute("current_book"));
        return new ModelAndView("payments/success", model);
    }

    @RequestMapping(value = "/payments/perform", method = RequestMethod.POST)
    public ModelAndView performPayment(@RequestParam("number") String number,
                                     @RequestParam("expiry") String expiry,
                                     @RequestParam("cvv") String cvv) {
        Map model = new HashMap();
        return new ModelAndView(new RedirectView("/payments/success", true), model);
    }

}
