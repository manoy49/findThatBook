package com.testvagrant.booknamechallenge.findthatbook.Service;

import com.testvagrant.booknamechallenge.findthatbook.Models.BookList;
import com.testvagrant.booknamechallenge.findthatbook.Models.SearchQueryParam;
import com.testvagrant.booknamechallenge.findthatbook.Utils.BookNotFoundException;
import org.springframework.stereotype.Component;

@Component
public interface Search {

    BookList findBooks(SearchQueryParam searchQueryParam) throws BookNotFoundException;
}
