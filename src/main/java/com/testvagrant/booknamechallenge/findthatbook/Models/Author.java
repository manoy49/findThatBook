package com.testvagrant.booknamechallenge.findthatbook.Models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Author {
    private int authorGRId;
    private String name;
    private String link;
}
