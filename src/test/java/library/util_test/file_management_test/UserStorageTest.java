package library.util_test.file_management_test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import library.exception.InvalidEmailException;
import library.exception.UserNotFoundException;
import library.model.user.ReaderUser;
import library.model.user.User;
import library.test_helper.comparator.UserFieldComparator;
import library.test_helper.fake_object_generators.FakeGenerator;
import library.util.file_managament.UserStorage;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserStorageTest {


    private static final String USER_DATA_FILENAME = "user_data.json";
    private final FakeGenerator fakeGenerator = new FakeGenerator();

    @Test
    public void put_PutCoupleOfUsers_UsersAppearsInTheJsonFile() throws IOException, InvalidEmailException {

        // Given

        UserStorage storage = new UserStorage();

        User originalUser1 = fakeGenerator.generateReaderUser();
        User originalUser2 = fakeGenerator.generateReaderUser();
        User originalUser3 = fakeGenerator.generateReaderUser();

        // When

        storage.put(originalUser1);
        storage.put(originalUser2);
        storage.put(originalUser3);

        ObjectMapper mapper = new ObjectMapper();

        File userFile = new File(USER_DATA_FILENAME);

        List<? extends User> userList = mapper.readValue(userFile, new TypeReference<List<? extends User>>() {
        });

        boolean correctInstancesCreated = userList.stream()
                .allMatch(user -> user instanceof ReaderUser);

        User fetchedUser1 = userList.get(0);
        User fetchedUser2 = userList.get(1);
        User fetchedUser3 = userList.get(2);

        // Then

        assertTrue(correctInstancesCreated);

        UserFieldComparator.compareUserProperties(originalUser1, fetchedUser1);
        UserFieldComparator.compareUserProperties(originalUser2, fetchedUser2);
        UserFieldComparator.compareUserProperties(originalUser3, fetchedUser3);
    }

    @Test
    public void get_PutCorrectUserData_GetCorrespondingUser() throws InvalidEmailException {

        // Given

        UserStorage storage = new UserStorage();

        User user1 = fakeGenerator.generateReaderUser();
        String user1Email = user1.getEmail();
        String user1Password = user1.getPassword();

        User user2 = fakeGenerator.generateReaderUser();
        String user2Email = user2.getEmail();
        String user2Password = user2.getPassword();

        User user3 = fakeGenerator.generateReaderUser();
        String user3Email = user3.getEmail();
        String user3Password = user3.getPassword();

        // When

        storage.put(user1);
        storage.put(user2);
        storage.put(user3);


        // Then

        try {
            User fetchedUser1 = storage.get(user1Email, user1Password);
            UserFieldComparator.compareUserProperties(user1, fetchedUser1);
            User fetchedUser2 = storage.get(user2Email, user2Password);
            UserFieldComparator.compareUserProperties(user2, fetchedUser2);
            User fetchedUser3 = storage.get(user3Email, user3Password);
            UserFieldComparator.compareUserProperties(user3, fetchedUser3);
        } catch (UserNotFoundException e) {
            throw new AssertionError("storage.get method failed to get user with correct credentials");
        }

    }

    @Test(expected = UserNotFoundException.class)
    public void get_PutIncorrectEmail_ThrowUserNotFoundException() throws UserNotFoundException, InvalidEmailException {
        // Given

        UserStorage storage = new UserStorage();

        User user = fakeGenerator.generateReaderUser();

        String userPassword = user.getPassword();
        String incorrectEmail = "test.test.test@notcodeacademy.lt";

        // When

        storage.put(user);
        User fetchedUser1 = storage.get(incorrectEmail, userPassword);
        UserFieldComparator.compareUserProperties(user, fetchedUser1);


        // Then
        /*
        UserNotFoundException should be thrown
        */

    }

    @Test(expected = UserNotFoundException.class)
    public void get_PutIncorrectPassword_ThrowUserNotFoundException() throws UserNotFoundException, InvalidEmailException {
        // Given

        UserStorage storage = new UserStorage();

        User user = fakeGenerator.generateReaderUser();

        String userEmail = user.getEmail();
        String incorrectPassword = ".'.'.'.'.'.incorrect password.'.'.'.'.'.";

        // When

        storage.put(user);
        User fetchedUser1 = storage.get(userEmail, incorrectPassword);
        UserFieldComparator.compareUserProperties(user, fetchedUser1);

        // Then

        /*
        UserNotFoundException should be thrown
        */

    }
}
