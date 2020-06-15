package com.testvagrant.booknamechallenge.findthatbook.Models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BookList {
    List<Book> books;
}
