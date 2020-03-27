package bookshop.system.bookshop.repositories;


import bookshop.system.bookshop.entities.AgeRestriction;
import bookshop.system.bookshop.entities.Book;
import bookshop.system.bookshop.entities.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> getAllByReleaseDateAfter(LocalDate localDate);

    List<Book> getAllByReleaseDateBefore(LocalDate localDate);

    List<Book> findBooksByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(String firstName, String lastName);

    List<Book> findByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findBooksByEditionTypeAndCopiesLessThan(EditionType editionType, int numOfCopies);

    List<Book> findBooksByPriceLessThanOrPriceGreaterThanOrderByTitleAsc(BigDecimal low, BigDecimal high);

    List<Book> findBooksByReleaseDateBeforeOrReleaseDateAfter(LocalDate before, LocalDate after);

    List<Book> findBooksByReleaseDateBefore(LocalDate localDate);

    List<Book> findByTitleContainingIgnoreCase(String str);

    List<Book> findBooksByAuthorLastNameStartingWithIgnoreCase(String str);

    @Query("SELECT b FROM Book b WHERE LENGTH(b.title) > :len ")
    List<Book> findBooksByTitleLength(@Param("len") int len);

    @Query("SELECT b.title, b.editionType, b.ageRestriction, b.price FROM Book b where b.title = :input ")
    List<Object> findBookByTitleSelectSomeFields(@Param("input") String inp);

    @Modifying
    @Query("UPDATE Book b SET b.copies = b.copies + :toAdd WHERE b.releaseDate > :releaseDt")
    int updateCopiesOfBooksReleasedAfter(@Param("toAdd") int toAdd, @Param("releaseDt") LocalDate localDate);

    @Modifying
    @Query("DELETE Book b WHERE b.copies < :amount")
    int deleteBooksWithLessThanCopies(@Param("amount") int amount);


}
