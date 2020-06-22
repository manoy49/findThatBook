package com.testvagrant.booknamechallenge.findthatbook.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BookList {
    List<Book> books;
}
