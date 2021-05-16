package library.model_test;

import library.entity.book.Book;
import library.entity.user.ReaderUser;
import library.entity.user.User;
import library.test_helper.fake_object_generators.FakeGenerator;
import library.util.collapser.BookListCollapser;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class WriterUserTest {

    private FakeGenerator fakeGenerator = new FakeGenerator();
    private BookListCollapser bookListCollapser = new BookListCollapser();

    @Test
    public void getBorrowedBooks_AddDuplicatesOfBooks_ReturnCollapsedList() {
        // Given

        User user = new ReaderUser("Name", "Surname", "hello.world@java.lt", "password123");
        Book book1 = fakeGenerator.generateBook();
        Book book2 = fakeGenerator.generateBook();
        Book book3 = fakeGenerator.generateBook();
        Book book4 = fakeGenerator.generateBook();

        int book1Quantity = book1.getQuantity();
        int book2Quantity = book2.getQuantity();

        user.borrowBook(book1);
        user.borrowBook(book1);
        user.borrowBook(book1);
        user.borrowBook(book1);
        user.borrowBook(book2);
        user.borrowBook(book2);
        user.borrowBook(book3);
        user.borrowBook(book4);

        List<Book> manuallyCollapsedBooks = new ArrayList<>();
        book1.add(book1Quantity * 3);
        book2.add(book2Quantity);
        manuallyCollapsedBooks.add(book1);
        manuallyCollapsedBooks.add(book2);
        manuallyCollapsedBooks.add(book3);
        manuallyCollapsedBooks.add(book4);



        // Then

        assertEquals(manuallyCollapsedBooks.toString(), user.getBorrowedBooks().toString());
    }
}
