package com.thoughtworks.go.website.controllers;

import com.thoughtworks.go.website.models.Book;
import com.thoughtworks.go.website.remote.service.BooksCollectionService;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class BooksControllerTest {

    @Test
    public void testShouldObtainAllBooks() throws Exception {
        BooksCollectionService service = mock(BooksCollectionService.class);
        List<Book> expected = Arrays.asList(new Book("isbn", "name", "author", "pub"));
        when(service.allBooks()).thenReturn(expected);

        BooksController controller = new BooksController(service);
        ModelAndView modelAndView = controller.allBooks(mock(HttpServletRequest.class));

        assertThat((List<Book>) modelAndView.getModel().get("books"), is(expected));
    }

    @Test
    public void testShouldRedirectToPaymentIfBookInStock() throws Exception {
        BooksController controller = new BooksController(mock(BooksCollectionService.class));

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession(true)).thenReturn(session);

        ModelAndView modelAndView = controller.buyBook("isbn", "name", request);

        RedirectView expected = new RedirectView("/payments/index", true);
        RedirectView actual = (RedirectView) modelAndView.getView();

        assertThat(actual.getUrl(), is("/payments/index"));
        assertThat(actual, instanceOf(expected.getClass()));
        verify(session).setAttribute("current_book", new BooksController.BookCookie("name", "isbn"));
        verifyNoMoreInteractions(session);
    }
}