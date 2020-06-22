package com.testvagrant.booknamechallenge.findthatbook.unit;

import com.testvagrant.booknamechallenge.findthatbook.model.Author;
import com.testvagrant.booknamechallenge.findthatbook.model.Book;
import com.testvagrant.booknamechallenge.findthatbook.model.BookList;
import com.testvagrant.booknamechallenge.findthatbook.model.SearchQueryParam;
import com.testvagrant.booknamechallenge.findthatbook.serviceImpl.GoodReadSearchImpl;
import com.testvagrant.booknamechallenge.findthatbook.utils.BookNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchImplTest {

    @Mock
    private GoodReadSearchImpl goodReadSearch;

    private SearchQueryParam searchQueryParam;

    @Before
    public void setUp() {
        this.searchQueryParam = new SearchQueryParam();
        searchQueryParam.setAuthorName("Test");
        searchQueryParam.setYear(2010);
    }

    @Test
    public void testFindBooks() throws BookNotFoundException {

        Book book = Book.builder().publicationYear(2010).author(Author.builder().name(this.searchQueryParam.getAuthorName()).build()).build();

        ArrayList books = new ArrayList();
        books.add(book);

        when(goodReadSearch.findBooks(this.searchQueryParam)).thenReturn(BookList.builder().books(books).build());

        BookList expectedBookList = goodReadSearch.findBooks(this.searchQueryParam);

        Assert.assertEquals(expectedBookList, BookList.builder().books(books).build());
    }

}
