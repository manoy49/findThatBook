package com.testvagrant.booknamechallenge.findthatbook.Models;

import lombok.Data;

@Data
public class SearchQueryParam {
    private String authorName;
    private String title;
    private String plot;
    private int isbn;
}
