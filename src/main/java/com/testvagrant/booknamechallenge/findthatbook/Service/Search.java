package com.testvagrant.booknamechallenge.findthatbook.Service;

import com.testvagrant.booknamechallenge.findthatbook.Models.BookList;
import com.testvagrant.booknamechallenge.findthatbook.Models.SearchQueryParam;
import org.springframework.stereotype.Component;

@Component
public interface Search {

    BookList findBooks(SearchQueryParam searchQueryParam) throws Exception;
}
