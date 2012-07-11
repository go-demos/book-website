package com.thoughtworks.go.website.controllers;

import com.thoughtworks.go.website.models.Book;
import com.thoughtworks.go.website.models.BookCookie;
import com.thoughtworks.go.website.remote.service.BooksCollectionService;
import com.thoughtworks.go.website.remote.service.BooksInventoryService;
import org.junit.Before;
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

    private BooksInventoryService inventoryService;

    @Before
    public void setUp() throws Exception {
        inventoryService = mock(BooksInventoryService.class);
    }

    @Test
    public void testShouldObtainAllBooks() throws Exception {
        BooksCollectionService service = mock(BooksCollectionService.class);
        List<Book> expected = Arrays.asList(new Book("isbn", "name", "author", "pub"));
        when(service.allBooks()).thenReturn(expected);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        BooksController controller = new BooksController(service, inventoryService);
        ModelAndView modelAndView = controller.allBooks(request);

        assertThat((List<Book>) modelAndView.getModel().get("books"), is(expected));
    }

    @Test
    public void shouldDisplayFlashWhenSetInSession() throws Exception {
        BooksCollectionService service = mock(BooksCollectionService.class);
        List<Book> expected = Arrays.asList(new Book("isbn", "name", "author", "pub"));
        when(service.allBooks()).thenReturn(expected);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("flash")).thenReturn("Foo");

        BooksController controller = new BooksController(service, inventoryService);
        ModelAndView modelAndView = controller.allBooks(request);

        assertThat((String) modelAndView.getModel().get("flash_message"), is("Foo"));
    }

    @Test
    public void testShouldRedirectToPaymentIfBookInStock() throws Exception {
        when(inventoryService.checkInventory("isbn")).thenReturn(true);
        BooksController controller = new BooksController(mock(BooksCollectionService.class), inventoryService);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession(true)).thenReturn(session);

        ModelAndView modelAndView = controller.buyBook("isbn", "name", request);

        RedirectView expected = new RedirectView("/payments/index", true);
        RedirectView actual = (RedirectView) modelAndView.getView();

        assertThat(actual.getUrl(), is("/payments/index"));
        assertThat(actual, instanceOf(expected.getClass()));
        verify(session).setAttribute("current_book", new BookCookie("name", "isbn"));
        verifyNoMoreInteractions(session);
    }

    @Test
    public void testShouldRedirectToIndexIfBookNotInStock() throws Exception {
        when(inventoryService.checkInventory("isbn")).thenReturn(false);
        BooksController controller = new BooksController(mock(BooksCollectionService.class), inventoryService);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession(true)).thenReturn(session);

        ModelAndView modelAndView = controller.buyBook("isbn", "name", request);

        RedirectView expected = new RedirectView("/payments/index", true);
        RedirectView actual = (RedirectView) modelAndView.getView();

        assertThat(actual.getUrl(), is("/books"));
        assertThat(actual, instanceOf(expected.getClass()));
        verify(session).setAttribute("flash", "Book sold out. Please try again in a couple of days.");
        verifyNoMoreInteractions(session);
    }
}