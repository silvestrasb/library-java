package library.controller;

import library.dto.BookDTO;
import library.dto.UserDTO;
import library.entity.Book;
import library.entity.User;
import library.service.BookService;
import library.service.BorrowService;
import library.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final BorrowService borrowService;
    private final BookService bookService;

    public UserController(UserService userService, BorrowService borrowService, BookService bookService) {
        this.userService = userService;
        this.borrowService = borrowService;
        this.bookService = bookService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getUsers() {
        return userService.findUsers().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO getUser(@PathVariable("id") Long userId) {
        return new UserDTO(userService.findUser(userId));
    }

    @PatchMapping("/{userId}/books")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrowBook(@PathVariable("userId") Long userId, @RequestParam(value = "bookId") Long bookId) {
        borrowService.borrow(userId, bookId);
    }

    @GetMapping("/{userId}/books")
    public List<BookDTO> getBorrowedBooks(@PathVariable("userId") Long userId) {
        return borrowService.getBorrowedBooks(userId).stream().map(BookDTO::new).collect(Collectors.toList());
    }

    @GetMapping("{userId}/return")
    public Book returnBook(@PathVariable("userId") User user, @RequestParam(value = "bookId") Long bookId) {
        user.getBorrowedBooks().removeIf(book1 -> book1.getId() == bookId);

        Book book = bookService.findById(bookId);
        book.setUser(null);
        userService.updateUser(user);
        return bookService.save(book);
    }

}
