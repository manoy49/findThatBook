package com.testvagrant.booknamechallenge.findthatbook.service;

import com.testvagrant.booknamechallenge.findthatbook.models.BookList;
import com.testvagrant.booknamechallenge.findthatbook.models.SearchQueryParam;
import org.springframework.stereotype.Component;

@Component
public interface Search {

    BookList findBooks(SearchQueryParam searchQueryParam) throws Exception;
}
