package com.testvagrant.booknamechallenge.findthatbook.serviceImpl;

import com.testvagrant.booknamechallenge.findthatbook.models.Author;
import com.testvagrant.booknamechallenge.findthatbook.models.Book;
import com.testvagrant.booknamechallenge.findthatbook.models.BookList;
import com.testvagrant.booknamechallenge.findthatbook.repository.BookRepository;
import com.testvagrant.booknamechallenge.findthatbook.utils.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GoodReadResponseProcessor {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    RestAPITemplate restAPITemplate;

    @Autowired
    QueryHelper queryHelper;

    @Autowired
    XmlParser xmlParser;

    public BookList saveAndProcessResults(List<String> xmlResponses, String... otherParam) throws BookNotFoundException {
        BookList result = new BookList();
        List books = new ArrayList();
        ArrayList finalBookIds = finalBookIDs(xmlResponses);

        for(Object bookId : finalBookIds) {

            String response = restAPITemplate.getResponse(queryHelper.getQueryString(bookId));
            HashMap keyValue = xmlParser.getNestedJSONs(response);
            Iterator iterator = keyValue.keySet().iterator();

            while (iterator.hasNext()) {
                Object key = iterator.next();

                if(key.equals("GoodreadsResponse")) {

                    JSONObject bookInfo = (JSONObject) keyValue.get(key);
                    bookInfo = (JSONObject) bookInfo.get("book");
                    JSONObject authorInfo = (JSONObject) bookInfo.get("authors");
                    Object authors = authorInfo.get("author");
                    authorInfo = authors instanceof JSONArray ? (JSONObject) ((JSONArray)authors).get(0)
                            : (JSONObject) authors;

                    Author author = new Author();
                    author.setAuthorGRId(authorInfo.getInt("id"));
                    author.setLink(authorInfo.get("link").toString());
                    author.setName(authorInfo.get("name").toString());

                    Book book = new Book();

                    book.setTitle(bookInfo.get("title").toString());
                    book.setBestBookId(bookInfo.getInt("id"));
                    book.setAuthor(author);
                    if(!bookInfo.get("average_rating").equals(""))
                        book.setAvg_rating(bookInfo.getDouble("average_rating"));
                    book.setDescription(bookInfo.getString("description"));
                    book.setImage_url(bookInfo.getString("image_url"));
                    if(!bookInfo.get("isbn").equals(""))
                    {
                        try {
                            book.setIsbn(bookInfo.getInt("isbn"));
                        }catch (Exception e) {
                            System.out.println("skip this books isbn");
                        }
                    }
                    book.setLink(bookInfo.get("url").toString());
                    if(!bookInfo.get("publication_year").equals(""))
                        book.setPublicationYear(bookInfo.getInt("publication_year"));

                    List<Book> toFind = bookRepository.findBookByBestBookId(book.getBestBookId());

                    if(toFind.size() == 0)
                        bookRepository.save(book);

                    if(otherParam.length > 0) {
                        if(book.getDescription().toLowerCase().contains(otherParam[0].toLowerCase())) {
                            books.add(book);
                        }
                        else if(otherParam[0].toLowerCase().contains(book.getDescription().toLowerCase())) {
                            books.add(book);
                        }
                        else if(book.getPublicationYear() == Integer.valueOf(otherParam[0])) {
                            books.add(book);
                        }
                        else {
                            throw new BookNotFoundException("Can not get book with given information. Try and Recall more. ");
                        }
                    }
                    else {
                        books.add(book);
                    }
                }

            }
        }

        result.setBooks(books);
        return result;
    }

    private ArrayList finalBookIDs(List<String> xmlResponses) {
        int nextResponseCounter = 0;

        ArrayList<JSONArray> responses  = new ArrayList<>();


        for(int item = 0; item < xmlResponses.size(); item++) {
            HashMap keyValue =  xmlParser.getNestedJSONs(xmlResponses.get(item));
            Set sets = keyValue.keySet();
            Iterator iterator = sets.iterator();
            while (iterator.hasNext()) {
                Object key = iterator.next();
                if(key.toString().equals("work")) {
                    responses.add((JSONArray) keyValue.get(key));
                }
            }
        }

        ArrayList bestBookIds = getBestBookIds(responses.get(nextResponseCounter));

        if(responses.size() > 1) {
            while (nextResponseCounter < responses.size()) {
                nextResponseCounter++;
                if(nextResponseCounter <= responses.size() - 1) {
                    ArrayList nextBestBookIds = getBestBookIds(responses.get(nextResponseCounter));
                    ArrayList updatedResults = UnionAndIntersectionFinder.findIntersection(bestBookIds, nextBestBookIds);
                    bestBookIds = updatedResults;
                }
            }
        }

        if(bestBookIds.isEmpty()) {
            for (int responseCount = 0; responseCount < responses.size(); responseCount++) {
                bestBookIds.add(getBestBookIds(responses.get(responseCount)).get(0));
            }
        }

        return bestBookIds;
    }

    private ArrayList getBestBookIds(JSONArray work) {
        ArrayList bookIds = new ArrayList();
        for(int i =0; i< work.length(); i++) {
            JSONObject bookInfo = (JSONObject) work.get(i);
            JSONObject bestBookInfo = (JSONObject) bookInfo.get("best_book");
            JSONObject bestBook = (JSONObject) bestBookInfo.get("id");
            bookIds.add(bestBook.get("content"));
        }

        return bookIds;
    }
}
