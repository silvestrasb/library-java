package library.util.file_managament;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import library.exception.AuthorDoesNotExistsException;
import library.exception.BookNotFoundException;
import library.entity.book.Book;
import library.util.collapser.BookListCollapser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookStorage implements Storage<Book> {

    private static final String BOOK_DATA_FILENAME = "book_data.json";

    private File bookFile = new File(BOOK_DATA_FILENAME);

    private List<Book> bookList = new ArrayList<>();

    private BookListCollapser bookListCollapser = new BookListCollapser();

    private ObjectMapper mapper = new ObjectMapper();

    public BookStorage() {
        read();
    }

    public void read(){
        try {
            bookList = mapper.readValue(bookFile, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void put(Book book) {
        read();
        bookList.add(book);
        this.update();
    }

    @Override
    public void update() {
        collapseBooks();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writer().writeValue(bookFile, bookList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Book findBookByTitle(String title) throws BookNotFoundException {
        read();
        update();
        Book foundBook = bookList.stream()
                .filter(book -> book.getTitle().equals(title))
                .findAny()
                .orElse(null);

        if (foundBook != null) {
            return foundBook;
        } else {
            throw new BookNotFoundException();
        }
    }

    // Keep in mind that the String author could be name, surname or both

    public List<Book> findBookByAuthor(String author) throws AuthorDoesNotExistsException {
        read();
        update();
        List<Book> foundByName = bookList.stream()
                .filter(book -> book.getAuthorsName().equals(author))
                .collect(Collectors.toList());

        if (foundByName.size() > 0) return foundByName;

        List<Book> foundBySurname = bookList.stream()
                .filter(book -> book.getAuthorsSurname().equals(author))
                .collect(Collectors.toList());

        if (foundBySurname.size() > 0) return foundBySurname;

        List<Book> foundByNameAndSurname = bookList.stream()
                .filter(book -> (book.getAuthorsName() + " " + book.getAuthorsSurname()).equals(author))
                .collect(Collectors.toList());

        if (foundByNameAndSurname.size() > 0) return foundByNameAndSurname;

        throw new AuthorDoesNotExistsException();
    }
    public void collapseBooks() {
        bookList = bookListCollapser.collapseBooks(bookList);
    }

    public void addToExistingBook(Book book) {
        read();
        Book booksThatMatchBorrowedBook = bookList.stream()
                .filter(book1 -> book1.getAuthorsName().equals(book.getAuthorsName()) &&
                        book1.getAuthorsSurname().equals(book.getAuthorsSurname()) &&
                        book1.getGenre().equals(book.getGenre()) &&
                        book1.getTitle().equals(book.getTitle())
                ).findAny()
                .get();

        bookList.remove(booksThatMatchBorrowedBook);

        booksThatMatchBorrowedBook.add(book.getQuantity());

        this.put(booksThatMatchBorrowedBook);
    }

    public List<Book> getBookList() {
        return this.bookList;
    }

    public void remove(Book book){
        Book booksThatMatchBorrowedBook = bookList.stream()
                .filter(book1 -> book1.getAuthorsName().equals(book.getAuthorsName()) &&
                        book1.getAuthorsSurname().equals(book.getAuthorsSurname()) &&
                        book1.getGenre().equals(book.getGenre()) &&
                        book1.getTitle().equals(book.getTitle())
                ).findAny()
                .get();
        bookList.remove(booksThatMatchBorrowedBook);
        update();
    }

}
