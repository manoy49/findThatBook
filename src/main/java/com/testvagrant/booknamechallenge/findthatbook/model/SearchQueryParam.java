package com.testvagrant.booknamechallenge.findthatbook.model;

import lombok.Data;

@Data
public class SearchQueryParam {
    private String authorName;
    private String title;
    private String plot;
    private int isbn;
    private int year;
}
