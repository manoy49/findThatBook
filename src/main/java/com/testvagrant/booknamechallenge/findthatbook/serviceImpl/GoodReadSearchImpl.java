package com.testvagrant.booknamechallenge.findthatbook.serviceImpl;

import com.testvagrant.booknamechallenge.findthatbook.constants.Constants;
import com.testvagrant.booknamechallenge.findthatbook.model.BookList;
import com.testvagrant.booknamechallenge.findthatbook.model.SearchQueryParam;
import com.testvagrant.booknamechallenge.findthatbook.repository.BookRepository;
import com.testvagrant.booknamechallenge.findthatbook.service.Search;
import com.testvagrant.booknamechallenge.findthatbook.utils.BookNotFoundException;
import com.testvagrant.booknamechallenge.findthatbook.utils.QueryHelper;
import com.testvagrant.booknamechallenge.findthatbook.utils.RestAPITemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class GoodReadSearchImpl implements Search {

    @Autowired
    QueryHelper queryHelper;
    @Autowired
    GoodReadResponseProcessor readResponseProcessor;
    @Autowired
    RestAPITemplate restAPITemplate;
    @Autowired
    LocalSearchImpl localSearch;

    @Override
    public BookList findBooks(SearchQueryParam searchQueryParam) throws BookNotFoundException {

        BookList result;

        /* checking for book in local DB first */
        result = localSearch.getBooksFromLocal(searchQueryParam);

        // good read api call in case of empty results
        if(result.getBooks().isEmpty() || result.getBooks() == null)
        {
            /* query for goodRead api */
            Map params = queryHelper.processQueryParams(searchQueryParam);

            if(searchQueryParam.getPlot() != null)
                return getResultsFromBookReadApi(params, searchQueryParam.getPlot());
            else if(searchQueryParam.getYear() != 0)
                return getResultsFromBookReadApi(params, String.valueOf(searchQueryParam.getYear()));
            else
                return getResultsFromBookReadApi(params);
        }

        log.info(" ====== Getting Results from local database ====== ");

        return result;
    }

    private BookList getResultsFromBookReadApi(Map params, String... otherParam) throws BookNotFoundException {
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

        log.info(" ======= Getting data from good read api ======= ");

        if(otherParam.length > 0) {
            return readResponseProcessor.saveAndProcessResults(responses, otherParam[0]);
        }

        // saving the books in db for future and getting book list
        return readResponseProcessor.saveAndProcessResults(responses);
    }

}
