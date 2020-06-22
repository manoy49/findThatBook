package com.testvagrant.booknamechallenge.findthatbook.service;

import com.testvagrant.booknamechallenge.findthatbook.model.BookList;
import com.testvagrant.booknamechallenge.findthatbook.model.SearchQueryParam;
import com.testvagrant.booknamechallenge.findthatbook.utils.BookNotFoundException;
import org.springframework.stereotype.Component;

@Component
public interface Search {

    BookList findBooks(SearchQueryParam searchQueryParam) throws BookNotFoundException;
}
