package com.testvagrant.booknamechallenge.findthatbook.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Builder
@Document
public class Book {
    private int isbn;
    private String title;
    private int publicationYear;
    private int bestBookId;
    private Author author;
    private String image_url;
    private double avg_rating;
    private String description;
    private String link;
}
