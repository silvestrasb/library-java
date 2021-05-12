package library.model.user;

import library.model.book.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class User {

    private String name;
    private String surname;
    private String email;
    private String password;
    private List<Book> borrowedBooks = new ArrayList<>();

}
