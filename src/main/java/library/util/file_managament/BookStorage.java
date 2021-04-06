package library.util.file_managament;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import library.exception.AuthorDoesNotExistsException;
import library.exception.BookNotFoundException;
import library.model.book.Book;
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

    @Override
    public void put(Book book) {

        bookList.add(book);
        this.update();
    }

    @Override
    public void update() {
        collapseBooks();
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writer().writeValue(bookFile, bookList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Book findBookByTitle(String title) throws BookNotFoundException {
        System.out.println(title);
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

        List<Book> foundByName = bookList.stream()
                .filter(book -> book.getAuthorsName().equals(author))
                .collect(Collectors.toList());

        if (foundByName.size() > 0) return foundByName;

        List<Book> foundBySurname = bookList.stream()
                .filter(book -> book.getAuthorsSurname().equals(author))
                .collect(Collectors.toList());

        if (foundBySurname.size() > 0) return foundBySurname;

        List<Book> foundByNameAndSurname = bookList.stream()
                .filter(book -> (book.getAuthorsName() + " " +book.getAuthorsSurname()).equals(author))
                .collect(Collectors.toList());

        if (foundByNameAndSurname.size() > 0) return foundByNameAndSurname;

        throw new AuthorDoesNotExistsException();
    }

    public void collapseBooks(){
        bookList = bookListCollapser.collapseBooks(bookList);
    }


    public void returnBook(Book book){
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

    public void borrowBook(Book book){

        for (Book book1: bookList){
            System.out.println(book1);
        }
        Book booksThatMatchBorrowedBook = bookList.stream()
                .filter(book1 -> book1.getAuthorsName().equals(book.getAuthorsName()) &&
                        book1.getAuthorsSurname().equals(book.getAuthorsSurname()) &&
                        book1.getGenre().equals(book.getGenre()) &&
                        book1.getTitle().equals(book.getTitle())
                ).findAny()
                .get();

        System.out.println(booksThatMatchBorrowedBook.getQuantity());

        bookList.remove(booksThatMatchBorrowedBook);


        booksThatMatchBorrowedBook.remove(book.getQuantity());


        this.put(booksThatMatchBorrowedBook);
    }
}
