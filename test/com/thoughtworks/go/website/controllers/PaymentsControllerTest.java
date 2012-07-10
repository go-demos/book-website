package com.thoughtworks.go.website.controllers;

import com.thoughtworks.go.website.models.BookCookie;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PaymentsControllerTest {

    @Test
    public void testShouldShowTheIndexPage() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        BookCookie bookCookie = new BookCookie("name", "isbn");
        when(session.getAttribute("current_book")).thenReturn(bookCookie);

        ModelAndView modelAndView = new PaymentsController().paymentIndex(request);

        assertThat(modelAndView.getViewName(), is("payments/index"));
        assertThat((BookCookie) modelAndView.getModel().get("book"), is(bookCookie));
    }

    @Test
    public void testShouldShowTheSuccessPage() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        BookCookie bookCookie = new BookCookie("name", "isbn");
        when(session.getAttribute("current_book")).thenReturn(bookCookie);

        ModelAndView modelAndView = new PaymentsController().paymentSuccessful(request);

        assertThat(modelAndView.getViewName(), is("payments/success"));
        assertThat((BookCookie) modelAndView.getModel().get("book"), is(bookCookie));
    }

    @Test
    public void testShouldShowThePerformPayment() throws Exception {
        ModelAndView modelAndView = new PaymentsController().performPayment("12", "12/12", "121");

        RedirectView view = (RedirectView) modelAndView.getView();
        assertThat(view.getUrl(), is("/payments/success"));
    }
}