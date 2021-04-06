package library.service.log;

import library.model.book.Book;
import library.model.user.User;

public interface UserActivityLogs<E extends User> {
    void logIn(E user);

    void register(E user);

    void logOut(E user);

    void userBorrow(E user, Book book);

    void userReturnBook(E user, Book book);
}
