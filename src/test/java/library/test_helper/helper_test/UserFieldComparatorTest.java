package library.test_helper.helper_test;

import library.exception.InvalidEmailException;
import library.model.book.Book;
import library.model.user.ReaderUser;
import library.model.user.User;
import library.test_helper.comparator.UserFieldComparator;
import library.test_helper.fake_object_generators.FakeGenerator;
import org.junit.Test;

public class UserFieldComparatorTest {

    private final FakeGenerator fakeGenerator = new FakeGenerator();

    @Test(expected = AssertionError.class)
    public void compareUserProperties_CompareDifferentUsers_throwAssertionError() throws InvalidEmailException {

        // Given

        User user1 = fakeGenerator.generateReaderUser();
        user1.borrowBook(new Book("Test","Test","Test","Test",123));
        User user2 = fakeGenerator.generateReaderUser();

        // When

        UserFieldComparator.compareUserProperties(user1, user2);

        // Then
        /*
        AssertionError should be thrown
        */
    }

    @Test
    public void compareUserProperties_CompareSameUsers_doNothing() throws InvalidEmailException {
        // Given

        User user = fakeGenerator.generateReaderUser();

        // When

        UserFieldComparator.compareUserProperties(user, user);

        // Then
        /*
        AssertionError should NOT be thrown
        */
    }

    @Test (expected = AssertionError.class)
    public void compareUserProperties_DifferentBookLists_ThrowInvalidEmailException() throws InvalidEmailException {

        // Given

        User user1 = new ReaderUser("Test","Test","Test","Test");
        user1.borrowBook(new Book("Test","Test","Test","Test",123));
        User user2 = new ReaderUser("Test","Test","Test","Test");
        user2.borrowBook(new Book("TEST", "Test", "Test", "Test", 123));

        // When

        UserFieldComparator.compareUserProperties(user1, user2);

        // Then
        /*
        AssertionError should NOT be thrown
        */
    }



//    @Test(expected = AssertionError.class)
//    public void compareBookList_CompareDifferentLists_throwAssertionError() {
//        // Given
//        User user1 = fakeGenerator.generateReaderUser();
//        User user2 = fakeGenerator.generateReaderUser();
//
//        // When
//
//        UserFieldComparator.compareBookListOfUsers(user1, user2);
//
//        // Then
//        /*
//        AssertionError should be thrown
//        */
//    }
}
