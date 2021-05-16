package library.test_helper.comparator;

import library.entity.book.Book;
import library.entity.user.ReaderUser;
import library.entity.user.User;

import java.util.Comparator;
import java.util.List;

public class UserFieldComparator {

    private static void compareBookList(List<Book> originalUser, List<Book> testUser) {
        if (originalUser.size() != testUser.size()){
            throw new AssertionError("Users have different book lists (user.getBorrowedBooks).");
        }
        for (Book originalUserBook : originalUser) {
            boolean foundBook = false;
            for (Book testUserBook : testUser) {
                int status = Comparator.comparing(Book::getGenre)
                        .thenComparing(Book::getQuantity)
                        .thenComparing(Book::getTitle)
                        .compare(testUserBook, originalUserBook);
                if (status == 0) {
                    foundBook = true;
                }
            }
            if (!foundBook) {
                throw new AssertionError("Users have different book lists (user.getBorrowedBooks).");
            }
        }
    }

    public static <E extends User> void compareUserProperties(E originalUser, E testUser) {

        int zeroIfAllFieldsExcludingEmailAreEqual = Comparator.comparing(User::getName)
                .thenComparing(User::getSurname)
                .thenComparing(User::getEmail)
                .thenComparing(User::getPassword)
                .compare(originalUser, testUser);

        compareBookList(originalUser.getBorrowedBooks(), testUser.getBorrowedBooks());


        if (zeroIfAllFieldsExcludingEmailAreEqual != 0) {
            throw new AssertionError("Users have different properties.");
        }
    }
}
