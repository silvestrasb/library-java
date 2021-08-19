package library.service;

import library.entity.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowService {
    private final BookService bookService;
    private final UserService userService;

    public BorrowService(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    public void borrow(Long userId, Long bookId) {
        Book book = bookService.findById(bookId);
        book.setUser(userService.findUser(userId));
        bookService.save(book);
    }

    public List<Book> getBorrowedBooks(Long userId) {
        return userService.findUser(userId).getBorrowedBooks();
    }
}
