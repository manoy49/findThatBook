package com.testvagrant.booknamechallenge.findthatbook.repository;

import com.testvagrant.booknamechallenge.findthatbook.models.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findBookByBestBookId(int bestBookId);
    List<Book> getBooksByAuthorNameContainingIgnoreCase(String authorName);
    List<Book> getBooksByTitleContainingIgnoreCase(String title);
    List<Book> getBooksByAuthorNameContainingIgnoreCaseAndTitleContainingIgnoreCase(String authorName, String title);
    List<Book> getBooksByDescriptionContainingIgnoreCaseAndAuthorNameContainingIgnoreCase(String description, String authorName);
    List<Book> getBooksByPublicationYearEqualsAndAuthorNameContainingIgnoreCase(int year, String authorName);
    List<Book> getBooksByPublicationYearEqualsAndAuthorNameContainingIgnoreCaseAndDescriptionContainingIgnoreCaseAndTitleContainingIgnoreCase(int year, String authorName, String description, String title);
    List<Book> getBooksByPublicationYearEqualsAndAuthorNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase(int year, String authorName, String description);

}
