package org.kruger.quarkus.microservices;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.kruger.quarkus.microservices.book.Book;
import org.kruger.quarkus.microservices.book.NumbersProxy;

import javax.inject.Inject;
import javax.json.JsonBuilderFactory;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Instant;

@Path("/api/books")
@Tag(name="Book REST Endpoint")
public class BookResource {

    @RestClient
    NumbersProxy numbersProxy;
    @Inject
    Logger logger;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Operation(
            summary = "Create a Book",
            description = "Create a Book with a ISBN"
    )
    @Fallback(fallbackMethod = "fallbackForCreateABook")
    @Retry(maxRetries = 3, delay = 3000)
    public Response createABook(@FormParam("title") String title,
                                @FormParam("author") String author,
                                @FormParam("year") int yearOfPublication,
                                @FormParam("genre") String genre) {
        Book book = new Book();
        book.isbn13 = numbersProxy.generateIsbnNumbers().isbn13;
        book.title = title;
        book.author =author;
        book.yearOfPublication = yearOfPublication;
        book.genre = genre;
        book.creationDate = Instant.now();
        logger.info("Book created " + book);
        return Response.status(201).entity(book).build();
    }

    public Response fallbackForCreateABook(String title, String author, int yearOfPublication, String genre) throws FileNotFoundException {
        Book book = new Book();
        book.isbn13 = "Will be set later";
        book.title = title;
        book.author =author;
        book.yearOfPublication = yearOfPublication;
        book.genre = genre;
        book.creationDate = Instant.now();
        saveBookOnDisk(book);
        logger.warn("Book saved on Disk " + book);
        return Response.status(206).entity(book).build();
    }

    private void saveBookOnDisk(Book book) throws FileNotFoundException {
        String bookJson = JsonbBuilder.create().toJson(book);
        try (PrintWriter out = new PrintWriter("book-" + Instant.now().toEpochMilli() + ".json")){
            out.println(bookJson);
        }
    }
}