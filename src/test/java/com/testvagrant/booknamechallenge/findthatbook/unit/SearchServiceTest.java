package com.testvagrant.booknamechallenge.findthatbook.unit;

import static org.mockito.Mockito.*;

import com.testvagrant.booknamechallenge.findthatbook.model.Author;
import com.testvagrant.booknamechallenge.findthatbook.model.Book;
import com.testvagrant.booknamechallenge.findthatbook.model.BookList;
import com.testvagrant.booknamechallenge.findthatbook.model.SearchQueryParam;
import com.testvagrant.booknamechallenge.findthatbook.service.Search;
import com.testvagrant.booknamechallenge.findthatbook.utils.BookNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchServiceTest {

    @Mock
    private Search search;

    private SearchQueryParam searchQueryParam;


    @Before
    public void setUp() {
        this.searchQueryParam = new SearchQueryParam();
        searchQueryParam.setAuthorName("Test");
    }

    @Test
    public void testFindBooks() throws BookNotFoundException {

        Author author = Author.builder().name("Test").build();
        Book book = Book.builder().author(author).build();
        List list = new ArrayList();
        list.add(book);

        BookList bookList = BookList.builder().books(list).build();
        when(search.findBooks(this.searchQueryParam)).thenReturn(bookList);

        BookList expectedBookList = search.findBooks(this.searchQueryParam);

        Assert.assertEquals(bookList, expectedBookList);
        Assert.assertEquals(bookList.getBooks().get(0).getAuthor().getName(), expectedBookList.getBooks().get(0).getAuthor().getName());
    }

    @Test
    public void testFindBooksWithNoResult() throws BookNotFoundException {

        when(search.findBooks(this.searchQueryParam)).thenReturn(BookList.builder().build());

        BookList expectedBookList = search.findBooks(this.searchQueryParam);

        Assert.assertTrue(expectedBookList.getBooks() == null);
    }

}
