# findThatBook
This will help in finding books based on mainly author and title. It might work for some plots too.

Search Book Controller : 
It has an api for searching a book by it's author, plot or title.

## To run this in your localhost : 

Install mongodb. 

Download Robo 3T for easier handling.
Connect to your localhost database.
Generally running on port 27017.

Install maven.

Change the goodreads.key in application.properties. (It currently has mine.)

Change server.port, if the port is already in use.

Run the Application.

Go to http://localhost:8082/swagger-ui.html#! to see the api response.

Few Rules to keep in mind while searching using query : 

1. Author name must start with Capital letter
2. I have made this time the author field optional but that will cause no data in most of the cases
3. Try searching with ex : "#whatsthatbookname where year is 2006 and author is Murakami" like this
4. If your local db has data then only searching like "#whatsthatbookname where year is 2006" or "#whatsthatbookname released in 2006" will work with local stored values
5. Plot description must be at least 50 chars 
