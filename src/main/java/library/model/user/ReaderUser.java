package library.model.user;

import library.model.book.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReaderUser extends User {
    List<Book> borrowedBooks;
}

