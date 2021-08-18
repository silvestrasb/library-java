package library.controller;

import library.dto.request.CreateBookRequest;
import library.dto.request.UpdateBookRequest;
import library.dto.response.CreateBookResponse;
import library.dto.response.UpdateBookResponse;
import library.entity.Book;
import library.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getBooks() {
        return bookService.findAll();
    }

    @GetMapping("{id}")
    public Book getBook(@PathVariable("id") Long bookId) {
        return bookService.getById(bookId);
    }

    @GetMapping("/search")
    public List<Book> searchByFields(
            @RequestParam(required = false, value = "title", defaultValue = "") String title,
            @RequestParam(required = false, value = "author", defaultValue = "") String author,
            @RequestParam(required = false, value = "genre", defaultValue = "") String genre
    ) {
        return bookService.findAllByFields(title, author, genre);
    }


    @DeleteMapping("{id}")
    public void deleteBook(@PathVariable("id") Long bookId) {
        bookService.deleteById(bookId);
    }

    @PostMapping
    public CreateBookResponse saveBook(@RequestBody CreateBookRequest createBookRequest) {
        return new CreateBookResponse(bookService.save(new Book(createBookRequest)));
    }

    // TODO: FIND THE APPROPRIATE BOOK FROM DB
    @PutMapping("{id}")
    public UpdateBookResponse updateBook(@PathVariable("id") Long bookId, @RequestBody UpdateBookRequest updateBookRequest) {
        return new UpdateBookResponse(bookService.save(new Book(bookId, updateBookRequest)));
    }


}

