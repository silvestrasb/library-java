package library.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import library.dto.BookDTO;
import library.dto.request.CreateBookRequest;
import library.dto.request.UpdateBookRequest;
import library.dto.response.CreateBookResponse;
import library.dto.response.UpdateBookResponse;
import library.entity.Book;
import library.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/books")
@Api(tags = "BookController")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ApiOperation(value = "Get all books", tags = "getBooks", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned list of books"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping
    public List<BookDTO> getBooks(@RequestParam(value = "available") boolean available) {
        if (available) {
            return bookService.findAllAvailable().stream().map(BookDTO::new).collect(Collectors.toList());
        }
        return bookService.findAllBooks().stream().map(BookDTO::new).collect(Collectors.toList());
    }

    @ApiOperation(value = "Get Book by id", tags = "getBook", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned book by id"),
            @ApiResponse(code = 404, message = "Book not found error"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN') || principal.id == #id")
    public BookDTO getBook(@PathVariable("id") Long bookId) {
        return new BookDTO(bookService.findById(bookId));
    }

    @ApiOperation(value = "Search books by fields", tags = "searchByFields", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned list of Books"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
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

    @ApiOperation(value = "Delete book by id", tags = "deleteBook", httpMethod = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted book by id"),
            @ApiResponse(code = 404, message = "Book not found error"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBook(@PathVariable("id") Long bookId) {
        bookService.deleteById(bookId);
    }

    @ApiOperation(value = "Save book", tags = "saveBook", httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully updated book"),
            @ApiResponse(code = 400, message = "Validation failed"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public CreateBookResponse saveBook(@RequestBody CreateBookRequest createBookRequest) {
        return new CreateBookResponse(bookService.save(new Book(createBookRequest)));
    }

    @ApiOperation(value = "Update book", tags = "updateBook", httpMethod = "PUT")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated book record"),
            @ApiResponse(code = 400, message = "Validation failed"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UpdateBookResponse updateBook(@PathVariable("id") Long bookId, @RequestBody UpdateBookRequest updateBookRequest) {
        return new UpdateBookResponse(bookService.update(new Book(bookId, updateBookRequest)));
    }


}

