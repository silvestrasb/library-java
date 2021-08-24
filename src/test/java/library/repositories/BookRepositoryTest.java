package library.repositories;

import library.entity.Book;
import library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testFindAllByAuthor() {
        // given
        // data from data.sql

        // when
        List<Book> books = bookRepository.findByFields("", "nas", "");

        // then
        assertEquals(3, books.size());
    }

    @Test
    public void testSearchBooksByGenre() {
        // given
        // data from data.sql

        // when
        List<Book> books = bookRepository.findByFields("","","poem");

        // then
        assertEquals(2, books.size());
    }



    @Test
    public void testSearchBooksByTitle() {
        // given
        // data from data.sql

        String TITLE = "Anykščių Šilelis";
        // when
        List<Book> books = bookRepository.findByFields(TITLE,"","");

        // then
        assertEquals(1, books.size());
        assertEquals(TITLE, books.get(0).getTitle());
    }

}
