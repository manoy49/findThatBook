package com.testvagrant.booknamechallenge.findthatbook.utils;

import com.testvagrant.booknamechallenge.findthatbook.constants.Constants;
import com.testvagrant.booknamechallenge.findthatbook.model.SearchQueryParam;
import org.springframework.stereotype.Component;

@Component
public class QueryProcessor {

   private String[] plotFilters = {"where ", "which "};

    public SearchQueryParam processQuery(String query) {
        query = query.trim();

        if(query.contains("#whatsthatbookname")) {
            query = query.split(Constants.QUERY_REGEX)[1].trim();
        }

        String author = getAuthorFromQuery(query);
        String plot = getPlotFromQuery(query);
        String year = getYearFromQuery(query);
        String title = getTitleFromQuery(query);

        System.out.println(author);
        System.out.println(plot);
        System.out.println(year);

        SearchQueryParam searchQueryParam = new SearchQueryParam();
        searchQueryParam.setAuthorName(author);
        searchQueryParam.setPlot(plot);
        searchQueryParam.setTitle(title);
        if(year != null)
            searchQueryParam.setYear(Integer.parseInt(year));
        return searchQueryParam;
    }

    private String getAuthorFromQuery(String query) {
        if(query.toLowerCase().contains("by ")) {
            String[] byCount = query.split("by ");
            if(byCount[1].trim().matches(Constants.AUTHOR_NAME_REGEX)) {
                return byCount[1].trim();
            }
        }
        if(query.toLowerCase().contains(Constants.SEARCH_FIELD_AUTHOR)) {
            String[] keywords = query.split(" ");
            if(keywords.length > 1) {
                for(int position = 0; position < keywords.length; position++) {
                    if(keywords[position].trim().matches(Constants.AUTHOR_NAME_REGEX)) {
                        return keywords[position].trim();
                    }
                }
            }

        }
        return null;
    }

    private String getPlotFromQuery(String query) {
        for (String keyword: plotFilters) {
            if(query.contains(keyword)) {
                String plot = query.split(keyword)[1];
                String year = getYearFromQuery(query);
                String author = getAuthorFromQuery(query);
                if(plot.contains("plot is")) {
                    plot = plot.split("plot is")[1].trim();
                }
                if(year != null) {
                    plot = pluckPlot(plot, year);
                }
                if(author == null || plot.length() > 50) {
                    if(author != null) {
                        if(pluckPlot(plot, author).length() != plot.length())
                            plot = pluckPlot(plot, author);
                        else if(plot.contains(author))
                            plot = plot.substring(plot.indexOf(author) + author.length() + 4);
                    }
                    return plot.trim();
                }
            }
        }
        return null;
    }

    private String pluckPlot(String plot, String... param) {
        int length = plot.length();
        int paramIndex = plot.indexOf(param[0]);

        if(length == paramIndex + param[0].length()) {
            if(plot.contains("and"))
                plot = plot.substring(0, plot.lastIndexOf("and")).trim();
        }
        return plot;
    }

    private String getYearFromQuery(String query) {
        String[] keywords = query.split(" ");
        for (String key: keywords) {
            if(key.trim().matches(Constants.YEAR_REGEX)) {
                return key.trim();
            }
        }
        return null;
    }

    private String getTitleFromQuery(String query) {
        if(query.toLowerCase().contains(Constants.SEARCH_FIELD_TITLE)) {
            query = query.split(Constants.SEARCH_FIELD_TITLE)[1].trim();
            if(query.toLowerCase().contains("is ")) {
                query = query.split("is ")[1].trim();
                return query.trim();
            }
            return query.trim();
        }
        return null;
    }
 }
