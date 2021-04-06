package library.util_test.file_management_test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import library.exception.AuthorDoesNotExistsException;
import library.exception.BookNotFoundException;
import library.model.book.Book;
import library.test_helper.fake_object_generators.FakeGenerator;
import library.util.file_managament.BookStorage;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookStorageTest {

    private FakeGenerator fakeGenerator = new FakeGenerator();
    private static final String BOOK_DATA_FILENAME = "book_data.json";

    @Test
    public void put_CoupleOfBooks_BooksAppearInJsonFile() throws IOException {

        BookStorage bookStorage = new BookStorage();

        Book book1 = fakeGenerator.generateBook();
        Book book2 = fakeGenerator.generateBook();
        Book book3 = fakeGenerator.generateBook();
        Book book4 = fakeGenerator.generateBook();
        Book book5 = fakeGenerator.generateBook();
        Book book6 = fakeGenerator.generateBook();
        Book book7 = fakeGenerator.generateBook();
        // When

        bookStorage.put(book1);
        bookStorage.put(book2);
        bookStorage.put(book3);
        bookStorage.put(book4);
        bookStorage.put(book5);
        bookStorage.put(book6);
        bookStorage.put(book7);

        ObjectMapper mapper = new ObjectMapper();

        File userFile = new File(BOOK_DATA_FILENAME);

        List<Book> bookList = mapper.readValue(userFile, new TypeReference<List<Book>>() {
        });


        Book fetchedBook1 = bookList.get(0);
        Book fetchedBook2 = bookList.get(1);
        Book fetchedBook3 = bookList.get(2);
        Book fetchedBook4 = bookList.get(3);
        Book fetchedBook5 = bookList.get(4);
        Book fetchedBook6 = bookList.get(5);
        Book fetchedBook7 = bookList.get(6);

        // Then

        assertEquals(book1.toString(),fetchedBook1.toString());
        assertEquals(book2.toString(),fetchedBook2.toString());
        assertEquals(book3.toString(),fetchedBook3.toString());
        assertEquals(book4.toString(),fetchedBook4.toString());
        assertEquals(book5.toString(),fetchedBook5.toString());
        assertEquals(book6.toString(),fetchedBook6.toString());
        assertEquals(book7.toString(),fetchedBook7.toString());
    }

    @Test
    public void findByTitle_correctTitle_returnCorrespondingBook() throws BookNotFoundException {
        // Given
        BookStorage bookStorage = new BookStorage();
        Book book = fakeGenerator.generateBook();
        bookStorage.put(book);


        String title = book.getTitle();

        // Then

        assertEquals(book.toString(), bookStorage.findBookByTitle(title).toString());

    }

    @Test
    public void findByAuthor_EnterAuthorsName_ReturnCorrectListOfBooksByAuthor()  throws
            AuthorDoesNotExistsException {

        BookStorage bookStorage = new BookStorage();

        Book book1 = new Book("Horrors of Testing.", "Lorem", "Ipsum", "Horror", 1);
        Book book2 = new Book("Space Testing.", "Lorem", "Ipsum", "Sci-fi", 2);
        Book book3 = new Book("Test, please don't leave!", "Lorem", "Ipsum", "Drama", 3);
        Book book4 = fakeGenerator.generateBook();
        Book book5 = fakeGenerator.generateBook();

        bookStorage.put(book1);
        bookStorage.put(book2);
        bookStorage.put(book3);
        bookStorage.put(book4);
        bookStorage.put(book5);

        List<Book> sameAuthorsBooks = new ArrayList<>();
        sameAuthorsBooks.add(book1);
        sameAuthorsBooks.add(book2);
        sameAuthorsBooks.add(book3);

        assertEquals(sameAuthorsBooks.toString(), bookStorage.findBookByAuthor("Lorem").toString());

    }

    @Test
    public void findByAuthor_EnterAuthorsSurname_ReturnCorrectListOfBooksByAuthor() throws
            AuthorDoesNotExistsException {

        BookStorage bookStorage = new BookStorage();

        Book book1 = new Book("Horrors of Testing.", "Lorem", "Ipsum", "Horror", 1);
        Book book2 = new Book("Space Testing.", "Lorem", "Ipsum", "Sci-fi", 2);
        Book book3 = new Book("Test, please don't leave!", "Lorem", "Ipsum", "Drama", 3);
        Book book4 = fakeGenerator.generateBook();
        Book book5 = fakeGenerator.generateBook();

        bookStorage.put(book1);
        bookStorage.put(book2);
        bookStorage.put(book3);
        bookStorage.put(book4);
        bookStorage.put(book5);

        List<Book> sameAuthorsBooks = new ArrayList<>();
        sameAuthorsBooks.add(book1);
        sameAuthorsBooks.add(book2);
        sameAuthorsBooks.add(book3);

        assertEquals(sameAuthorsBooks.toString(), bookStorage.findBookByAuthor("Ipsum").toString());

    }

    @Test
    public void findByAuthor_EnterAuthorsNameAndSurname_ReturnCorrectListOfBooksByAuthor() throws

            AuthorDoesNotExistsException {

        BookStorage bookStorage = new BookStorage();

        Book book1 = new Book("Horrors of Testing.", "Lorem", "Ipsum", "Horror", 1);
        Book book2 = new Book("Space Testing.", "Lorem", "Ipsum", "Sci-fi", 2);
        Book book3 = new Book("Test, please don't leave!", "Lorem", "Ipsum", "Drama", 3);
        Book book4 = fakeGenerator.generateBook();
        Book book5 = fakeGenerator.generateBook();

        bookStorage.put(book1);
        bookStorage.put(book2);
        bookStorage.put(book3);
        bookStorage.put(book4);
        bookStorage.put(book5);

        List<Book> sameAuthorsBooks = new ArrayList<>();
        sameAuthorsBooks.add(book1);
        sameAuthorsBooks.add(book2);
        sameAuthorsBooks.add(book3);

        assertEquals(sameAuthorsBooks.toString(), bookStorage.findBookByAuthor("Lorem Ipsum").toString());

    }

    @Test (expected = AuthorDoesNotExistsException.class)
    public void findByAuthor_EnterIncorrectString_ThrowAuthorDoesNotExistsException() throws
            AuthorDoesNotExistsException {

        BookStorage bookStorage = new BookStorage();

        Book book1 = new Book("Horrors of Testing.", "Lorem", "Ipsum", "Horror", 1);
        Book book2 = new Book("Space Testing.", "Lorem", "Ipsum", "Sci-fi", 2);
        Book book3 = new Book("Test, please don't leave!", "Lorem", "Ipsum", "Drama", 3);
        Book book4 = fakeGenerator.generateBook();
        Book book5 = fakeGenerator.generateBook();

        bookStorage.put(book1);
        bookStorage.put(book2);
        bookStorage.put(book3);
        bookStorage.put(book4);
        bookStorage.put(book5);


        bookStorage.findBookByAuthor("IncorrectString");

    }

    @Test
    public void returnBorrowedBook_ReturnOneBook_oneLessInList() throws BookNotFoundException {
        BookStorage bookStorage = new BookStorage();

        Book book1 = fakeGenerator.generateBook();
        Book book2 = fakeGenerator.generateBook();
        Book book3 = fakeGenerator.generateBook();
        Book book4 = fakeGenerator.generateBook();
        Book bookį = fakeGenerator.generateBook();
        Book book5 = new Book("Horrors of Testing.", "Lorem", "Ipsum", "Horror", 10);


        bookStorage.put(book1);
        bookStorage.put(book2);
        bookStorage.put(book3);
        bookStorage.put(book4);
        bookStorage.put(book5);

        Book returnBook = new Book("Horrors of Testing.", "Lorem", "Ipsum", "Horror", 1);

        bookStorage.returnBook(returnBook);

        Book book = bookStorage.findBookByTitle("Horrors of Testing.");

        assertEquals(11, book.getQuantity());

    }

    @Test
    public void returnBorrowedBook_ReturnCoupleSameBooks_BookListIsExpectedlySmaller() throws BookNotFoundException {
        BookStorage bookStorage = new BookStorage();

        Book book1 = fakeGenerator.generateBook();
        Book book2 = fakeGenerator.generateBook();
        Book book3 = fakeGenerator.generateBook();
        Book book4 = fakeGenerator.generateBook();
        Book bookį = fakeGenerator.generateBook();
        Book book5 = new Book("Horrors of Testing.", "Lorem", "Ipsum", "Horror", 100);
        Book returnBook = new Book("Horrors of Testing.", "Lorem", "Ipsum", "Horror", 30);


        bookStorage.put(book1);
        bookStorage.put(book2);
        bookStorage.put(book3);
        bookStorage.put(book4);
        bookStorage.put(book5);

        bookStorage.returnBook(returnBook);

        Book book = bookStorage.findBookByTitle("Horrors of Testing.");

        assertEquals(130, book.getQuantity());

    }
}
