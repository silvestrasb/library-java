package lt.library.model.user;

import lt.library.model.book.Book;
import java.util.List;

public interface Borrowing {

    void borrowBook(Book book);

    List<Book> getBorrowedBooks();
}
