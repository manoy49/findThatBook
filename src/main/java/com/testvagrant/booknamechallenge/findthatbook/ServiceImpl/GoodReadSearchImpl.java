package com.testvagrant.booknamechallenge.findthatbook.ServiceImpl;

import com.testvagrant.booknamechallenge.findthatbook.Constants.Constants;
import com.testvagrant.booknamechallenge.findthatbook.Models.BookList;
import com.testvagrant.booknamechallenge.findthatbook.Models.SearchQueryParam;
import com.testvagrant.booknamechallenge.findthatbook.Repository.BookRepository;
import com.testvagrant.booknamechallenge.findthatbook.Service.Search;
import com.testvagrant.booknamechallenge.findthatbook.Utils.BookNotFoundException;
import com.testvagrant.booknamechallenge.findthatbook.Utils.QueryHelper;
import com.testvagrant.booknamechallenge.findthatbook.Utils.RestAPITemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GoodReadSearchImpl implements Search {

    @Autowired
    QueryHelper queryHelper;
    @Autowired
    GoodReadResponseProcessor readResponseProcessor;
    @Autowired
    RestAPITemplate restAPITemplate;
    @Autowired
    BookRepository bookRepository;

    private static Logger logger = LoggerFactory.getLogger(GoodReadSearchImpl.class);

    @Override
    public BookList findBooks(SearchQueryParam searchQueryParam) throws Exception {
        BookList result;
        Map params = queryHelper.processQueryParams(searchQueryParam);
        result = getBooksFromLocal(searchQueryParam);
        if(result.getBooks().isEmpty() || result.getBooks() == null)
        {
            if(searchQueryParam.getPlot() != null)
                return getResultsFromBookReadApi(params, searchQueryParam.getPlot());
            else
                return getResultsFromBookReadApi(params);
        }
        logger.info(" ====== Getting Results from local database ====== ");
        return result;
    }

    private BookList getResultsFromBookReadApi(Map params, String... plot) throws Exception {
        List<String> responses = new ArrayList<>();

        if(params.containsKey(Constants.MULTIPLE) && (Boolean) params.get(Constants.MULTIPLE)) {
            String searchFor[] = params.get(Constants.Query).toString().split(",");
            if( searchFor.length > 0) {
                for(int count = 0; count < searchFor.length; count++) {
                    params.replace(Constants.Query, searchFor[count]);
                    if(count == 0)
                        params.replace(Constants.SEARCH_FIELD_KEY, Constants.SEARCH_FIELD_TITLE);
                    else
                        params.replace(Constants.SEARCH_FIELD_KEY, Constants.SEARCH_FIELD_AUTHOR);
                    params.remove(Constants.MULTIPLE);
                    responses.add(restAPITemplate.getSearchResults(params));
                }
            }
        }
        else {
            responses.add(restAPITemplate.getSearchResults(params));
        }
        logger.info(" ======= Getting data from good read api ======= ");
        if(plot.length > 0) {
            try {
                return readResponseProcessor.saveAndProcessResults(responses, plot[0]);
            }catch (BookNotFoundException e) {
                throw e;
            }
        }
        return readResponseProcessor.saveAndProcessResults(responses);
    }

    private BookList getBooksFromLocal(SearchQueryParam searchQueryParam) {
        BookList result = new BookList();

        if((searchQueryParam.getTitle() != null && !searchQueryParam.getTitle().isEmpty()) && (searchQueryParam.getAuthorName() != null && !searchQueryParam.getAuthorName().isEmpty())){
            result.setBooks(bookRepository.getBooksByAuthorNameContainingIgnoreCaseAndTitleContainingIgnoreCase(searchQueryParam.getAuthorName(), searchQueryParam.getTitle()));
        }
        else if((searchQueryParam.getAuthorName() != null || !searchQueryParam.getAuthorName().isEmpty()) && searchQueryParam.getPlot() != null) {
            result.setBooks(bookRepository.getBooksByDescriptionContainingIgnoreCaseAndAuthorNameContainingIgnoreCase(searchQueryParam.getPlot(), searchQueryParam.getAuthorName()));
        }
        else if(searchQueryParam.getTitle() != null && !searchQueryParam.getTitle().isEmpty()) {
           result.setBooks(bookRepository.getBooksByTitleContainingIgnoreCase(searchQueryParam.getTitle()));
        }
        else if(searchQueryParam.getAuthorName() != null && !searchQueryParam.getAuthorName().isEmpty()) {
            result.setBooks(bookRepository.getBooksByAuthorNameContainingIgnoreCase(searchQueryParam.getAuthorName()));
        }
        else {
            result.setBooks(null);
        }
        return result;
    }

}
