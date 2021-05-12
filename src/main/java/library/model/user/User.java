package library.model.user;

import library.model.book.Book;
import lombok.Getter;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class User implements Borrow {

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

}
