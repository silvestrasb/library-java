package library.util_test.file_management_test;

import library.HibernateConfig;
import library.entity.Book;
import library.exception.AuthorDoesNotExistsException;
import library.exception.BookNotFoundException;
import library.service.BookService;
import library.test_helper.fake_object_generators.FakeGenerator;
import org.junit.Test;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BookServiceTest {

    private final BookService bookService = new BookService();
    private final List<Book> testBooks = new ArrayList<>();
    private FakeGenerator fakeGenerator = new FakeGenerator();

    @AfterAll
    static void afterAll() {
        HibernateConfig.closeSessionFactory();
    }


    @Test
    public void findByTitle_correctTitle_returnCorrespondingBook() throws BookNotFoundException {
        // Given
        HibernateConfig.buildSessionFactory();
Book book = fakeGenerator.generateBook();
        bookService.save(book);


        String title = book.getTitle();

        // Then

        assertEquals(book.toString(), bookService.findBookByTitle(title).toString());

        bookService.delete(book);

    }

    @Test
    public void findByAuthor_EnterAuthorsName_ReturnCorrectListOfBooksByAuthor() throws
            AuthorDoesNotExistsException {

        HibernateConfig.buildSessionFactory();

        Book book1 = new Book("Horrors of Testing.", "Lorem", "Ipsum", "Horror");
        Book book2 = new Book("Space Testing.", "Lorem", "Ipsum", "Sci-fi");
        Book book3 = new Book("Test, please don't leave!", "Lorem", "Ipsum", "Drama");
        Book book4 = fakeGenerator.generateBook();
        Book book5 = fakeGenerator.generateBook();

        bookService.save(book1);
        bookService.save(book2);
        bookService.save(book3);

        List<Book> sameAuthorsBooks = new ArrayList<>();
        sameAuthorsBooks.add(book1);
        sameAuthorsBooks.add(book2);
        sameAuthorsBooks.add(book3);

        assertEquals(sameAuthorsBooks.toString(), bookService.findBookByAuthor("Lorem", "Ipsum").toString());

        bookService.delete(book1);
        bookService.delete(book2);
        bookService.delete(book3);
        HibernateConfig.closeSessionFactory();
    }


    @Test(expected = AuthorDoesNotExistsException.class)
    public void findByAuthor_EnterIncorrectString_ThrowAuthorDoesNotExistsException() throws
            AuthorDoesNotExistsException {

        HibernateConfig.buildSessionFactory();

        Book book1 = new Book("Horrors of Testing.", "Lorem", "Ipsum", "Horror");
        Book book2 = new Book("Space Testing.", "Lorem", "Ipsum", "Sci-fi");
        Book book3 = new Book("Test, please don't leave!", "Lorem", "Ipsum", "Drama");
        Book book4 = fakeGenerator.generateBook();
        Book book5 = fakeGenerator.generateBook();

        bookService.save(book1);
        bookService.save(book2);
        bookService.save(book3);
        bookService.save(book4);
        bookService.save(book5);

        try {
            bookService.findBookByAuthor("IncorrectString", "IncorrectString");
        } finally {
            bookService.delete(book1);
            bookService.delete(book2);
            bookService.delete(book3);
            bookService.delete(book4);
            bookService.delete(book5);
        }


    }

}
