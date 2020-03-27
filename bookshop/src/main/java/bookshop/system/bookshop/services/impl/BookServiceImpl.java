package bookshop.system.bookshop.services.impl;


import bookshop.system.bookshop.entities.*;
import bookshop.system.bookshop.repositories.BookRepository;
import bookshop.system.bookshop.services.AuthorService;
import bookshop.system.bookshop.services.BookService;
import bookshop.system.bookshop.services.CategoryService;
import bookshop.system.bookshop.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static bookshop.system.bookshop.constants.GlobalConstants.BOOKS_FILE_PATH;


@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final FileUtil fileUtil;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService, FileUtil fileUtil) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedBooks() throws IOException {
        if (this.bookRepository.count() != 0) {
            return;
        }

        String[] fileContent = this.fileUtil
                .readFileContent(BOOKS_FILE_PATH);

        Arrays.stream(fileContent)
                .forEach(r -> {
                    String[] data = r.split("\\s+");

                    Author author = this.getRandomAuthor();

                    EditionType editionType = EditionType.values()[Integer.parseInt(data[0])];

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                    LocalDate releaseDate = LocalDate.parse(data[1], formatter);

                    int copies = Integer.parseInt(data[2]);

                    BigDecimal price = new BigDecimal(data[3]);

                    AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(data[4])];

                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 5; i < data.length; i++) {
                        stringBuilder.append(data[i]).append(" ");
                    }
                    String title = stringBuilder.toString().trim();

                    Book book = new Book();
                    book.setAgeRestriction(ageRestriction);
                    book.setAuthor(author);
                    book.setCopies(copies);
                    book.setEditionType(editionType);
                    book.setPrice(price);
                    book.setReleaseDate(releaseDate);
                    book.setTitle(title);
                    book.setCategories(this.getRandomCategories());

                    this.bookRepository.saveAndFlush(book);
                    System.out.println();
                });

    }

    @Override
    public List<Book> booksAfter2000() {
        LocalDate releaseDate = LocalDate.of(2000, 12, 31);

        return this.bookRepository.getAllByReleaseDateAfter(releaseDate);
    }

    @Override
    public List<Book> booksBefore1990() {
        LocalDate releaseDate = LocalDate.of(1991, 1, 1);
        return this.bookRepository.getAllByReleaseDateBefore(releaseDate);

    }

    @Override
    public List<Book> findBooksByAuthorFullName(String firstName, String lastName) {
        return this.bookRepository.findBooksByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(firstName, lastName);
    }

    @Override
    public List<Book> findByAgeRestriction(AgeRestriction ageRestriction) {
        return this.bookRepository.findByAgeRestriction(ageRestriction);
    }

    @Override
    public List<Book> getGoldenBooks() {
        return this.bookRepository.findBooksByEditionTypeAndCopiesLessThan(EditionType.GOLD, 5000);
    }

    @Override
    public List<Book> getBooksWithLessThanOrMoreThanPrice(BigDecimal low, BigDecimal high) {
        return this.bookRepository.findBooksByPriceLessThanOrPriceGreaterThanOrderByTitleAsc(low, high);
    }

    @Override
    public List<Book> getBooksNotReleasedIn(String year) {
        LocalDate beforeYear = LocalDate.of(Integer.parseInt(year), 1, 1);
        LocalDate afterYear = LocalDate.of(Integer.parseInt(year), 12, 31);
        return this.bookRepository.findBooksByReleaseDateBeforeOrReleaseDateAfter(beforeYear, afterYear);
    }

    @Override
    public List<Book> getBooksBeforeReleaseDate(String date) {
        int[] dateInput = Arrays.stream(date.trim().split("-")).mapToInt(Integer::parseInt).toArray();
        LocalDate localDate = LocalDate.of(dateInput[2], dateInput[1], dateInput[0]);
        return this.bookRepository.findBooksByReleaseDateBefore(localDate);
    }

    @Override
    public List<Book> getBooksByStrInTitle(String str) {
        return this.bookRepository.findByTitleContainingIgnoreCase(str);
    }

    @Override
    public List<Book> getBooksByAuthorLastNameStartingWith(String str) {
        return this.bookRepository.findBooksByAuthorLastNameStartingWithIgnoreCase(str);
    }

    @Override
    public List<Book> getBooksByTitleLength(int length) {
        return this.bookRepository.findBooksByTitleLength(length);
    }

    @Override
    public List<Object> getBookFieldsByTitle(String input) {
        return this.bookRepository.findBookByTitleSelectSomeFields(input);
    }

    @Override
    public int updateBooksAfterReleaseDate(int toAdd, String date) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd MMM yyyy"));

        return this.bookRepository.updateCopiesOfBooksReleasedAfter(toAdd, localDate);
    }

    @Override
    public int deleteBooksWithLessThanCopies(int copies) {
        return this.bookRepository.deleteBooksWithLessThanCopies(copies);
    }

    private Set<Category> getRandomCategories() {
        int numOfCategories = new Random().nextInt(3) + 1;
        Set<Category> categories = new HashSet<>();
        for (int i = 1; i <= numOfCategories; i++) {
            categories.add(this.categoryService.getCategoryById((long) i));
        }
        return categories;
    }

    private Author getRandomAuthor() {
        Random random = new Random();
        int randomId = random.nextInt(this.authorService.getAuthorsCount()) + 1;

        return this.authorService.getAuthorById((long) randomId);
    }
}
