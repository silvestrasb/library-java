package library.controller;

import library.dto.BookDTO;
import library.dto.UserDTO;
import library.service.BorrowService;
import library.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final BorrowService borrowService;

    public UserController(UserService userService, BorrowService borrowService) {
        this.userService = userService;
        this.borrowService = borrowService;
    }

    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.findUsers().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    @GetMapping("{id}")
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
}
