package bookshop.system.bookshop.services;


import bookshop.system.bookshop.entities.Author;

import java.io.IOException;
import java.util.List;


public interface AuthorService {
    void seedAuthors() throws IOException;

    int getAuthorsCount();

    Author getAuthorById(Long id);

    List<Author> findAllAuthorsByCountOfBooksDesc();

    List<Author> getAuthorsByEndOfFirstName(String str);

    List<Author> getAllAuthors();


}
