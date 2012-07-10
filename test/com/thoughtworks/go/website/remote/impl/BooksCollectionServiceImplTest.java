package com.thoughtworks.go.website.remote.impl;

import com.thoughtworks.go.website.http.HttpClientWrapper;
import com.thoughtworks.go.website.models.Book;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BooksCollectionServiceImplTest {

    @Test
    public void testInitializeTheHttpClientProperly() throws Exception {
        BooksCollectionServiceImpl service = new BooksCollectionServiceImpl();
        assertThat(service.getHttpClientWrapper().baseUrl(), is("http://testhost:8080"));
    }

    @Test
    public void testParsingBooksResource() throws Exception {
        BooksCollectionServiceImpl service = new BooksCollectionServiceImpl();
        HttpClientWrapper wrapper = mock(HttpClientWrapper.class);
        when(wrapper.get("/book_management/api/books")).thenReturn(file("sample_books_resource.xml"));
        service.setHttpWrapper(wrapper);

        assertThat(service.allBooks(), is(Arrays.asList(new Book("isbn1", "first", "person1", "pub1"), new Book("isbn2", "second", "person2", "pub2"))));
    }

    @Test
    public void shouldReturnAnEmptyBooksListIfTheRemoteThrowsUp() throws Exception {
        BooksCollectionServiceImpl service = new BooksCollectionServiceImpl();
        assertThat(service.allBooks().isEmpty(), is(true));
    }

    private String file(String name) throws IOException {
        return IOUtils.toString(this.getClass().getClassLoader().getResource(name).openStream());
    }
}
