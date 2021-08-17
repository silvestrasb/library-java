package library.service;

import library.entity.Book;
import library.exception.BookNotFoundException;
import library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findAllByFields(String title, String author, String genre) {
        return bookRepository.findByFields(title, author, genre);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book getById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    public void deleteById(Long bookId) {
        bookRepository.deleteById(bookId);
    }
}
