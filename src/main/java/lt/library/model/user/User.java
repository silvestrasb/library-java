package lt.library.model.user;

import lt.library.model.Namable;
import lt.library.model.book.Book;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;

public class User implements Namable, Registrable, Borrowing {

    private final String name;
    private final String surname;
    private final String email;
    private final String passwordEncrypted;
    private List<Book> borrowedBooks = new ArrayList<>();

    public User(String name, String surname, String email, String password) { //, UserType type
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.passwordEncrypted = DigestUtils.sha256Hex(password);
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
    public String getHashedPassword() {
        return this.passwordEncrypted;
    }

    @Override
    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    @Override
    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

}
