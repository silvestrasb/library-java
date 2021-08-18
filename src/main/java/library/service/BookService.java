package library.service;

import library.entity.Book;
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

    public List<Book> findAllAvailable() {
        return bookRepository.findAll().stream().filter((b) -> b.getUser() == null).collect(Collectors.toList());
    }

    public List<Book> findAllByFields(String title, String author, String genre) {
        return bookRepository.findByFields(title, author, genre);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book getById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public void deleteById(Long bookId) {
        bookRepository.deleteById(bookId);
    }
}
