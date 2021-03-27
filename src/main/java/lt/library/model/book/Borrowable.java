package lt.library.model.book;

public interface Borrowable {
    void borrowBooks(Book book);

    int getBorrowedBooks();
}
