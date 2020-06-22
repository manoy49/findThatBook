package com.testvagrant.booknamechallenge.findthatbook.utils;

import com.testvagrant.booknamechallenge.findthatbook.constants.Constants;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class RestAPITemplate {

    public String getSearchResults(Map params) {
        RestTemplate restTemplate = new RestTemplate();
        String param = params.toString().replace("{", "?").replace("}", "").replace(", ", "&");
        String response = restTemplate.getForObject(Constants.GOODREAD_URL + param, String.class);
        return response;
    }

    public String getResponse(String params) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(Constants.GOODREAD_SHOW_BOOK_URL + params, String.class);
    }
}
