package com.testvagrant.booknamechallenge.findthatbook.serviceImpl;

import com.testvagrant.booknamechallenge.findthatbook.model.BookList;
import com.testvagrant.booknamechallenge.findthatbook.model.SearchQueryParam;
import com.testvagrant.booknamechallenge.findthatbook.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocalSearchImpl {

    @Autowired
    BookRepository bookRepository;

    public BookList getBooksFromLocal(SearchQueryParam searchQueryParam) {

        String title = searchQueryParam.getTitle();
        String author = searchQueryParam.getAuthorName();
        int year = searchQueryParam.getYear();
        String plot = searchQueryParam.getPlot();

        BookList result = BookList.builder().build();

        if(title != null && author != null && year != 0 && plot != null) {
            result = getBooksForAll(title, author, year, plot);
        }
        else if(author != null && year != 0 && plot != null) {
            result = getBooksForAuthorYearAndPlot(author, year, plot);
        }
        else if(title != null && author != null) {
            result = getBooksForTitleAndAuthor(title, author);
        }
        else if(author != null && plot != null) {
            result = getBooksForAuthorAndPlot(author, plot);
        }
        else if(author != null && year != 0) {
            result = getBooksForAuthorAndYear(author, year);
        }
        else if(title != null && !title.isEmpty()) {
            result.setBooks(bookRepository.getBooksByTitleContainingIgnoreCase(title));
        }
        else if(author != null && !author.isEmpty()) {
            result.setBooks(bookRepository.getBooksByAuthorNameContainingIgnoreCase(author));
        }
        else if(year != 0) {
            result.setBooks(bookRepository.getBooksByPublicationYearEquals(year));
        }
        else {
            result.setBooks(null);
        }
        return result;
    }

    private BookList getBooksForAll(String title, String author, int year, String plot) {

        if(!title.isEmpty() && !author.isEmpty() && !plot.isEmpty()) {
            return BookList.
                    builder().
                    books(bookRepository.getBooksByPublicationYearEqualsAndAuthorNameContainingIgnoreCaseAndDescriptionContainingIgnoreCaseAndTitleContainingIgnoreCase(
                    year, author, plot, title)).build();
        }

        return null;
    }

    private BookList getBooksForAuthorYearAndPlot(String author, int year, String plot) {

        if(!author.isEmpty() && !plot.isEmpty()) {
            return BookList.
                    builder().
                    books(bookRepository.getBooksByPublicationYearEqualsAndAuthorNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase(
                    year, author, plot)).build();
        }

        return null;
    }

    private BookList getBooksForTitleAndAuthor(String title, String author) {

        if(!author.isEmpty() && !title.isEmpty()) {

            return BookList.
                    builder().
                    books(bookRepository.getBooksByAuthorNameContainingIgnoreCaseAndTitleContainingIgnoreCase(author, title)).build();
        }

        return null;
    }

    private BookList getBooksForAuthorAndPlot(String author, String plot) {

        if(!author.isEmpty() && !plot.isEmpty()) {

            return BookList.
                    builder().
                    books(bookRepository.getBooksByDescriptionContainingIgnoreCaseAndAuthorNameContainingIgnoreCase(plot, author)).build();
        }

        return null;
    }

    private BookList getBooksForAuthorAndYear(String author, int year) {

        if(!author.isEmpty()) {
            return BookList.
                    builder().
                    books(bookRepository.getBooksByPublicationYearEqualsAndAuthorNameContainingIgnoreCase(year, author)).build();
        }

        return null;
    }

}
