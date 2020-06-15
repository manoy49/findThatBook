package com.testvagrant.booknamechallenge.findthatbook.controller;

import com.testvagrant.booknamechallenge.findthatbook.models.BookList;
import com.testvagrant.booknamechallenge.findthatbook.models.SearchQueryParam;
import com.testvagrant.booknamechallenge.findthatbook.service.Search;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("find")
    public ResponseEntity findBook(@RequestParam(required = false) String title,
                                   @RequestParam String author,
                                   @RequestParam(required = false) String plot,
                                   @RequestParam(required = false) Integer year) throws Exception {

           SearchQueryParam searchQueryParam =  new SearchQueryParam();
           searchQueryParam.setTitle(title);
           searchQueryParam.setAuthorName(author);
           searchQueryParam.setPlot(plot);
           if(year != null)
               searchQueryParam.setYear(year);
           BookList result = search.findBooks(searchQueryParam);
           log.info("Result of the " + searchQueryParam.toString() +" query is : " + result.getBooks().toString());

           return new ResponseEntity(result, HttpStatus.ACCEPTED);
    }
}
