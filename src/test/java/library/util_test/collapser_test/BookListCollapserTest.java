package library.util_test.collapser_test;

import library.entity.book.Book;
import library.test_helper.fake_object_generators.FakeGenerator;
import library.util.collapser.BookListCollapser;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class BookListCollapserTest {

    private FakeGenerator fakeGenerator = new FakeGenerator();
    private BookListCollapser bookListCollapser = new BookListCollapser();

    @Test
    public void collapseBooks_BooksWithSameFields_returnCollapsedList() {

        // Given

        Book book1 = new Book("Test", "Test", "Test", "Test", 100);
        Book book2 = new Book("Test", "Test", "Test", "Test", 300);
        List<Book> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);

        List<Book> collapsedBookListManual = new ArrayList<>();
        collapsedBookListManual.add(new Book("Test", "Test", "Test", "Test", 400));

        // Then

        assertEquals(collapsedBookListManual.toString(), bookListCollapser.collapseBooks(collapsedBookListManual).toString());

    }

    @Test
    public void collapseBooks_OneBook_returnThatBook() {

        // Given

        Book book = fakeGenerator.generateBook();

        List<Book> bookList = new ArrayList<>();
        bookList.add(book);

        // When

        assertEquals(bookList.toString(), bookListCollapser.collapseBooks(bookList).toString());
    }

    @Test
    public void collapseBooks_MixedBooks_ReturnCollapsedList() {

        // Given

        Book book1 = fakeGenerator.generateBook();
        Book book2 = new Book("1", "2", "3", "4", 1);
        Book book3 = new Book("1", "2", "3", "4", 1);
        Book book4 = new Book("1", "2", "3", "4", 100);
        Book book5 = fakeGenerator.generateBook();
        Book book6 = new Book("a", "b", "c", "d", 1);
        Book book7 = new Book("a", "b", "c", "d", 300);
        Book book8 = fakeGenerator.generateBook();
        Book book9 = fakeGenerator.generateBook();

        Book book124Collapsed = new Book("1", "2", "3", "4", 102);
        Book book67Collapsed = new Book("a", "b", "c", "d", 301);

        List<Book> bookList = new ArrayList<>();

        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);
        bookList.add(book4);
        bookList.add(book5);
        bookList.add(book6);
        bookList.add(book7);
        bookList.add(book8);
        bookList.add(book9);


        List<Book> manuallyCollapsedList = new ArrayList<>();

        manuallyCollapsedList.add(book1);
        manuallyCollapsedList.add(book124Collapsed);
        manuallyCollapsedList.add(book5);
        manuallyCollapsedList.add(book67Collapsed);
        manuallyCollapsedList.add(book8);
        manuallyCollapsedList.add(book9);

        // Then

        assertEquals(manuallyCollapsedList.toString(), bookListCollapser.collapseBooks(bookList).toString());
    }


    }
