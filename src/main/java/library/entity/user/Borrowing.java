package library.entity.user;

import library.entity.book.Book;

import java.util.List;

public interface Borrowing {

    void borrowBook(Book book);

    List<Book> getBorrowedBooks();
}
