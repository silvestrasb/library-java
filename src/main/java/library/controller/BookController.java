package library.controller;

import library.dto.BookDTO;
import library.dto.request.CreateBookRequest;
import library.dto.request.UpdateBookRequest;
import library.dto.response.CreateBookResponse;
import library.dto.response.UpdateBookResponse;
import library.entity.Book;
import library.service.BookService;
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
    public List<BookDTO> getBooks() {
        return bookService.findAllAvailable().stream().map(BookDTO::new).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public BookDTO getBook(@PathVariable("id") Long bookId) {
        return new BookDTO(bookService.getById(bookId));
    }

    @GetMapping("/search")
    public List<BookDTO> searchByFields(
            @RequestParam(required = false, value = "title", defaultValue = "") String title,
            @RequestParam(required = false, value = "author", defaultValue = "") String author,
            @RequestParam(required = false, value = "genre", defaultValue = "") String genre
    ) {
        return bookService.findAllByFields(title, author, genre).stream().map(BookDTO::new).collect(Collectors.toList());
    }


    @DeleteMapping("{id}")
    public void deleteBook(@PathVariable("id") Long bookId) {
        bookService.deleteById(bookId);
    }

    @PostMapping
    public CreateBookResponse saveBook(@RequestBody CreateBookRequest createBookRequest) {
        return new CreateBookResponse(bookService.save(new Book(createBookRequest)));
    }

    @PutMapping("{id}")
    public UpdateBookResponse updateBook(@PathVariable("id") Long bookId, @RequestBody UpdateBookRequest updateBookRequest) {
        return new UpdateBookResponse(bookService.save(new Book(bookId, updateBookRequest)));
    }

}

