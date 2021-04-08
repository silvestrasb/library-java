package library.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import library.exception.BookNotFoundException;
import library.model.Nameable;
import library.model.book.Book;
import library.util.collapser.BookListCollapser;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = WriterUser.class, name = "WriterUser"),
        @JsonSubTypes.Type(value = ReaderUser.class, name = "ReaderUser")}
)

public abstract class User implements Nameable, Registrable, Borrowing {

    @JsonProperty("type")
    private String type = this.getClass().getSimpleName(); // Needed for json polymorphic deserialization
    private String name;
    private String surname;
    private String email;
    private String password;
    private List<Book> borrowedBooks = new ArrayList<>();

    public User() {
    }

    public User(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = DigestUtils.sha256Hex(password);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getSurname() {
        return this.surname;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    @Override
    public void borrowBook(Book book) {
        BookListCollapser bookListCollapser = new BookListCollapser();

        borrowedBooks.add(book);

        borrowedBooks = bookListCollapser.collapseBooks(borrowedBooks);
    }

    public void returnBorrowedBook(Book book) throws BookNotFoundException {

        BookListCollapser bookListCollapser = new BookListCollapser();

        Book bookFromTheList = borrowedBooks.stream()
                .filter(b -> b.getAuthorsName().equals(book.getAuthorsName()) &&
                        b.getAuthorsSurname().equals(book.getAuthorsSurname()) &&
                        b.getTitle().equals(book.getTitle()))
                .findFirst()
                .orElse(null);

        if (bookFromTheList == null) {
            throw new BookNotFoundException();
        }


        borrowedBooks.remove(book);

        if (bookFromTheList.getQuantity() != 0) borrowedBooks.add(bookFromTheList);
        borrowedBooks = bookListCollapser.collapseBooks(borrowedBooks);
    }
}
