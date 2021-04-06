package library.service.log;

import library.model.book.Book;
import library.model.user.User;

public class UserActivityLogsImpl<E extends User> implements UserActivityLogs<E> {
    @Override
    public void logIn(E user) {
        
    }

    @Override
    public void register(E user) {

    }

    @Override
    public void logOut(E user) {

    }

    @Override
    public void userBorrow(E user, Book book) {

    }

    @Override
    public void userReturnBook(E user, Book book) {

    }
}
