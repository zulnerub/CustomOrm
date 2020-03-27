package bookshop.system.bookshop.controllers;


import bookshop.system.bookshop.entities.AgeRestriction;
import bookshop.system.bookshop.entities.Book;
import bookshop.system.bookshop.services.AuthorService;
import bookshop.system.bookshop.services.BookService;
import bookshop.system.bookshop.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;


@Controller
public class AppController implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final BufferedReader bufferedReader;

    @Autowired
    public AppController(CategoryService categoryService, AuthorService authorService, BookService bookService, BufferedReader bufferedReader) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.bufferedReader = bufferedReader;
    }

    @Override
    public void run(String... args) throws Exception {
       //   this.categoryService.seedCategories();
       //   this.authorService.seedAuthors();
       //   this.bookService.seedBooks();

        //ex1

        // List<Book> books = this.bookService.booksAfter2000();

        //ex2

      /*  List<Book> books2 = this.bookService.booksBefore1990();
        Set<Book> setBooks = new HashSet<>(books2);
        setBooks.forEach(b -> System.out.printf("%s %s\n", b.getAuthor().getFirstName(), b.getAuthor().getLastName()));
        //Ex3
        // this.authorService.findAllAuthorsByCountOfBooksDesc().forEach(a -> {System.out.printf("%s %s %d\n", a.getFirstName(), a.getLastName(), a.getBooks().size());});

        //Ex4
        System.out.println("Please enter author full name:");
        String[] authorNames = bufferedReader.readLine().split("\\s+");
        this.bookService.findBooksByAuthorFullName(authorNames[0], authorNames[1])
                .forEach(b -> {
                    System.out.printf("%s, %s, %d\n", b.getTitle(), b.getReleaseDate(), b.getCopies());
                });

       */
      /// ADVANCED QUERY
        //ex1
     // String ar = bufferedReader.readLine();
     // this.findByAgeRes(ar);
        //ex2
      // this.getGoldenBooks();
        //ex 3
        //BigDecimal low = new BigDecimal(bufferedReader.readLine());
        //BigDecimal high = new BigDecimal(bufferedReader.readLine());
        //this.getBooksByPriceLimit(low, high);

        //ex4
       // String year = this.bufferedReader.readLine();
        //this.getBooksNotReleasedInYear(year);
        // ex 5
       // String input = bufferedReader.readLine();
       // this.getBooksReleasedAfter(input);

        //ex6
       // String str = bufferedReader.readLine().trim();
       // this.getAuthorsByFirstNameEndingWith(str);
        //ex7
       // String str = bufferedReader.readLine().trim();
        //this.getBooksTitlesByStrInTitle(str);
        //ex8
        //String str = bufferedReader.readLine().trim();
        //this.getBooksByAuthorLastNameStartingWith(str);
        //ex9
       // int length = Integer.parseInt(bufferedReader.readLine());
        //this.getBooksByLengthOfTitle(length);
        //ex10
        //this.getTotalCopiesForAllAuthors();
        //ex11
    //  String input = bufferedReader.readLine().trim();
    //   this.getBookFieldsByTitle(input);

        //12
      //  String dataInput = bufferedReader.readLine();
      //  int copiesToAdd = Integer.parseInt(bufferedReader.readLine());

      //  System.out.println(this.updateBooksCopiesAfterReleaseDate(copiesToAdd, dataInput));
        //13
        int amount = Integer.parseInt(bufferedReader.readLine());
        System.out.println(this.deleteBooksWithLessThanCopies(amount));


    }

    private int deleteBooksWithLessThanCopies(int amount) {
        return this.bookService.deleteBooksWithLessThanCopies(amount);
    }

    private int updateBooksCopiesAfterReleaseDate(int copiesToAdd, String dataInput) {
        return this.bookService.updateBooksAfterReleaseDate(copiesToAdd, dataInput) * copiesToAdd;
    }

    private void getBookFieldsByTitle(String input) {
        this.bookService.getBookFieldsByTitle(input)
                .forEach(System.out::println);

    }

    private void getTotalCopiesForAllAuthors() {
        this.authorService.getAllAuthors()
                .forEach(a -> {
                    int copies = a.getBooks()
                            .stream()
                            .mapToInt(Book::getCopies)
                            .sum();
                    System.out.printf("%s %s - %d%n", a.getFirstName(), a.getLastName(), copies);
                });
    }

    private void getBooksByLengthOfTitle(int length) {
        System.out.println(this.bookService.getBooksByTitleLength(length).size());

    }

    private void getBooksByAuthorLastNameStartingWith(String str) {
        this.bookService.getBooksByAuthorLastNameStartingWith(str)
                .forEach(b -> System.out.printf("%s (%s %s)%n", b.getTitle(), b.getAuthor().getFirstName(), b.getAuthor().getLastName()));
    }

    private void getBooksTitlesByStrInTitle(String str) {
        this.bookService.getBooksByStrInTitle(str)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void getAuthorsByFirstNameEndingWith(String str) {
        this.authorService.getAuthorsByEndOfFirstName(str)
                .forEach(a -> System.out.printf("%s %s%n", a.getFirstName(), a.getLastName()));
    }

    private void getBooksReleasedAfter(String input) {
        this.bookService.getBooksBeforeReleaseDate(input)
                .forEach(b -> System.out.printf("%s %s %.2f%n", b.getTitle(), b.getEditionType(), b.getPrice()));
    }

    private void getBooksNotReleasedInYear(String year) {
        this.bookService.getBooksNotReleasedIn(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void getBooksByPriceLimit(BigDecimal low, BigDecimal high) {
        bookService.getBooksWithLessThanOrMoreThanPrice(low,high)
                .forEach(b -> System.out.printf("%s - $%.2f%n", b.getTitle(), b.getPrice()));
    }

    private void getGoldenBooks() {
        bookService.getGoldenBooks()
                .forEach(b -> System.out.println(b.getTitle()));
    }

    private void findByAgeRes(String ar) {
        bookService.findByAgeRestriction(AgeRestriction.valueOf(ar.toUpperCase()))
                .forEach(b -> System.out.println(b.getTitle()));
    }
}
