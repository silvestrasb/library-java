package library.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "Get all users", tags = "getUsers", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned all users."),
            @ApiResponse(code = 400, message = "Validation failed"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getUsers() {
        return userService.findUsers().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    @ApiOperation(value = "Get user by id", tags = "getUser", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned user."),
            @ApiResponse(code = 400, message = "Validation failed"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO getUser(@PathVariable("id") Long userId) {
        return new UserDTO(userService.findUser(userId));
    }

    @ApiOperation(value = "Borrow book", tags = "borrowBook", httpMethod = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully borrowed book."),
            @ApiResponse(code = 400, message = "Validation failed"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @PatchMapping("/{userId}/books")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrowBook(@PathVariable("userId") Long userId, @RequestParam(value = "bookId") Long bookId) {
        borrowService.borrow(userId, bookId);
    }

    @ApiOperation(value = "Get borrowed books", tags = "getBorrowedBooks", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned borrowed books."),
            @ApiResponse(code = 400, message = "Validation failed"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping("/{userId}/books")
    public List<BookDTO> getBorrowedBooks(@PathVariable("userId") Long userId) {
        return borrowService.getBorrowedBooks(userId).stream().map(BookDTO::new).collect(Collectors.toList());
    }

    @ApiOperation(value = "Return book", tags = "returnBook", httpMethod = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned a book."),
            @ApiResponse(code = 400, message = "Validation failed"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @PatchMapping("{userId}/return")
    public Book returnBook(@PathVariable("userId") User user, @RequestParam(value = "bookId") Long bookId) {
        user.getBorrowedBooks().removeIf(book1 -> book1.getId() == bookId);

        Book book = bookService.findById(bookId);
        book.setUser(null);
        userService.updateUser(user);
        return bookService.save(book);
    }

}
