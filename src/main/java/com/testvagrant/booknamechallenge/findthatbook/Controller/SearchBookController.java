package com.testvagrant.booknamechallenge.findthatbook.Controller;

import com.testvagrant.booknamechallenge.findthatbook.Models.BookList;
import com.testvagrant.booknamechallenge.findthatbook.Models.SearchQueryParam;
import com.testvagrant.booknamechallenge.findthatbook.Service.Search;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/")
public class SearchBookController {

    @Autowired
    Search search;

    private static Logger LOG = LoggerFactory.getLogger(SearchBookController.class);

    @GetMapping("find")
    public ResponseEntity findBook(@RequestParam(required = false) String title, @RequestParam String author, @RequestParam(required = false) String plot) throws Exception {
        BookList result;
        try {
           SearchQueryParam searchQueryParam =  new SearchQueryParam();
           searchQueryParam.setTitle(title);
           searchQueryParam.setAuthorName(author);
           searchQueryParam.setPlot(plot);
           result = search.findBooks(searchQueryParam);
           LOG.info("Result of the " + searchQueryParam.toString() +" query is : " + result.getBooks().toString());
        }catch (Exception e) {
            throw e;
        }
        return new ResponseEntity(result, HttpStatus.ACCEPTED);
    }
}
