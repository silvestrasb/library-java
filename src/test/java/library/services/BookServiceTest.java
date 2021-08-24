package library.services;

import library.entity.Book;
import library.entity.User;
import library.repository.BookRepository;
import library.service.BookService;
import library.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserService userService;

    @Mock
    private Book book;

    @Mock
    private User user;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private BookService bookService;

    @Test
    public void testCreateBook() {
        when(bookRepository.save(book)).thenReturn(book);

        bookService.save(book);

        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void testGetBooksByAuthor() {

        when(bookRepository.findByFields("", "Putinas", "")).thenReturn(List.of(book));

        List<Book> cars = bookService.findAllByFields("", "Putinas", "");

        verify(bookRepository, times(1)).findByFields("", "Putinas", "");
        assertFalse(cars.isEmpty());

    }
}
