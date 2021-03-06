package com.testvagrant.booknamechallenge.findthatbook.utils;

import com.testvagrant.booknamechallenge.findthatbook.constants.Constants;
import com.testvagrant.booknamechallenge.findthatbook.model.SearchQueryParam;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class QueryHelper {


    public Map processQueryParams(SearchQueryParam searchQueryParam) throws BookNotFoundException {
       Map<String, Object> params = setSearchFields(searchQueryParam);
       try {
           if(params.get(Constants.SEARCH_FIELD_KEY).equals(Constants.SEARCH_FIELD_ALL)) {
               params.put(Constants.MULTIPLE, true);
           }
       }catch (NullPointerException exception) {
           throw new BookNotFoundException("Sorry! We can not find your book with given data. Please trying adding author name or clear description");
       }

       return params;
    }

    private Map setSearchFields(SearchQueryParam searchQueryParam) {

        Map<String, Object> params = new HashMap<>();

        if(!(searchQueryParam.getAuthorName() == null || searchQueryParam.getAuthorName().isEmpty())  && !(searchQueryParam.getTitle() == null || searchQueryParam.getTitle().isEmpty())) {
            params.put(Constants.SEARCH_FIELD_KEY, Constants.SEARCH_FIELD_ALL);
            params.put(Constants.Query, searchQueryParam.getTitle() + ", " + searchQueryParam.getAuthorName());
        }
        else if(!(searchQueryParam.getAuthorName() == null || searchQueryParam.getAuthorName().isEmpty())) {
            params.put(Constants.SEARCH_FIELD_KEY, Constants.SEARCH_FIELD_AUTHOR);
            params.put(Constants.Query, searchQueryParam.getAuthorName());
        }
        else if(!(searchQueryParam.getTitle() == null || searchQueryParam.getTitle().isEmpty())){
            params.put(Constants.SEARCH_FIELD_KEY, Constants.SEARCH_FIELD_TITLE);
            params.put(Constants.Query, searchQueryParam.getTitle());
        }
        try {
            params.put(Constants.KEY_NAME, PropertiesLoader.loadProperties("application.properties").getProperty("goodreads.key"));

        }catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return params;
    }

    public String getQueryString(Object bookId) {
        String queryString = "?";
        try {
            queryString = queryString + Constants.KEY_NAME + "=" + PropertiesLoader.loadProperties("application.properties").getProperty("goodreads.key");
        }catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        queryString = queryString + "&" + Constants.BEST_BOOK_ID + "=" + bookId;
        return queryString;
    }
}
