package bookshop.system.bookshop.services.impl;


import bookshop.system.bookshop.entities.Author;
import bookshop.system.bookshop.repositories.AuthorRepository;
import bookshop.system.bookshop.services.AuthorService;
import bookshop.system.bookshop.utils.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static bookshop.system.bookshop.constants.GlobalConstants.AUTHORS_FILE_PATH;


@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {


    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;

    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (this.authorRepository.count() != 0) {
            return;
        }

        String[] authors = fileUtil
                .readFileContent(AUTHORS_FILE_PATH);

        Arrays.stream(authors)
                .forEach(r -> {
                    String[] names = r.split("\\s+");
                    Author author = new Author(names[0], names[1]);
                    authorRepository.saveAndFlush(author);
                });


    }

    @Override
    public int getAuthorsCount() {
        return (int) this.authorRepository.count();
    }

    @Override
    public Author getAuthorById(Long id) {
        return this.authorRepository.findById(id).get();
    }

    @Override
    public List<Author> findAllAuthorsByCountOfBooksDesc() {
        return this.authorRepository.findAuthorsByCountOfBooks();
    }

    @Override
    public List<Author> getAuthorsByEndOfFirstName(String str) {
        return this.authorRepository.findAuthorByFirstNameEndsWith(str);
    }

    @Override
    public List<Author> getAllAuthors() {
        return this.authorRepository.findAll();
    }


}
