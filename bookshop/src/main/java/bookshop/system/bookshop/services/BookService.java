package bookshop.system.bookshop.services;



import bookshop.system.bookshop.entities.AgeRestriction;
import bookshop.system.bookshop.entities.Book;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> booksAfter2000();

    List<Book> booksBefore1990();

    List<Book> findBooksByAuthorFullName(String firstName, String lastName);

    List<Book> findByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> getGoldenBooks();

    List<Book> getBooksWithLessThanOrMoreThanPrice(BigDecimal low, BigDecimal high);

    List<Book> getBooksNotReleasedIn(String year);

    List<Book> getBooksBeforeReleaseDate(String date);

    List<Book> getBooksByStrInTitle(String str);

    List<Book> getBooksByAuthorLastNameStartingWith(String str);

    List<Book> getBooksByTitleLength(int length);

    List<Object> getBookFieldsByTitle(String input);

    int updateBooksAfterReleaseDate(int toAdd, String localDate);

    int deleteBooksWithLessThanCopies(int copies);
}
