package library.model.user;

import library.model.book.Book;
import java.util.List;

public interface Borrow {

    void borrowBook(Book book);

    List<Book> getBorrowedBooks();
}
