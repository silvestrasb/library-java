package library.service;

import library.entity.Book;
import library.exception.BookNotFoundException;
import library.exception.ResourceNotFoundException;
import library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> findAllAvailable() {
        return this.findAllBooks().stream().filter((b) -> b.getUser() == null).collect(Collectors.toList());
    }

    public List<Book> findAllByFields(String title, String author, String genre) {
        return bookRepository.findByFields(title, author, genre);
    }

    public List<Book> findAllByFieldsAvailable(String title, String author, String genre) {
        return this.findAllByFields(title, author, genre).stream().filter((b) -> b.getUser() == null).collect(Collectors.toList());
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book update(Book book){
        Book bookDB = this.findById(book.getId());
        book.setUser(bookDB.getUser());
        return this.save(this.findById(book.getId()) != null ? book : null);
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    public void deleteById(Long bookId) {
        bookRepository.deleteById(bookId);
    }
}
