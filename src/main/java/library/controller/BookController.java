package library.controller;

import library.dto.BookDTO;
import library.dto.request.CreateBookRequest;
import library.dto.request.UpdateBookRequest;
import library.dto.response.CreateBookResponse;
import library.dto.response.UpdateBookResponse;
import library.entity.Book;
import library.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDTO> getAllBooks(@RequestParam(value = "available") boolean available) {
        if (available) {
            return bookService.findAllAvailable().stream().map(BookDTO::new).collect(Collectors.toList());
        }
        return bookService.findAllBooks().stream().map(BookDTO::new).collect(Collectors.toList());
    }

    // TODO: ONLY FOR ADMIN OR USER WHO BORROWED THAT BOOK
    @GetMapping("{id}")
    public BookDTO getBook(@PathVariable("id") Long bookId) {
        return new BookDTO(bookService.findById(bookId));
    }

    @GetMapping("/search")
    public List<BookDTO> searchByFields(
            @RequestParam(value = "available") boolean available,
            @RequestParam(required = false, value = "title", defaultValue = "") String title,
            @RequestParam(required = false, value = "author", defaultValue = "") String author,
            @RequestParam(required = false, value = "genre", defaultValue = "") String genre
    ) {
        if (available) {
            return bookService.findAllByFieldsAvailable(title, author, genre).stream().map(BookDTO::new).collect(Collectors.toList());
        }
        return bookService.findAllByFields(title, author, genre).stream().map(BookDTO::new).collect(Collectors.toList());
    }


    // TODO: ONLY F0R ADMIN
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable("id") Long bookId) {
        bookService.deleteById(bookId);
    }

    // TODO: ONLY F0R ADMIN
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateBookResponse saveBook(@RequestBody CreateBookRequest createBookRequest) {
        return new CreateBookResponse(bookService.save(new Book(createBookRequest)));
    }

    // TODO: ONLY F0R ADMIN
    @PutMapping("{id}")
    public UpdateBookResponse updateBook(@PathVariable("id") Long bookId, @RequestBody UpdateBookRequest updateBookRequest) {
        return new UpdateBookResponse(bookService.update(new Book(bookId, updateBookRequest)));
    }
}

