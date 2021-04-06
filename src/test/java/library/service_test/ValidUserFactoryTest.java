package library.service_test;

import library.model.enums.UserType;
import library.exception.InvalidEmailException;
import library.model.user.ReaderUser;
import library.model.user.User;
import library.service.factory.ValidUserFactory;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidUserFactoryTest {

    private ValidUserFactory validUserFactory = new ValidUserFactory();

    @Test
    public void ValidUserFactory_givingUserParameters_returnUser() throws InvalidEmailException {
        User user = validUserFactory.getValidUser(UserType.REGULAR_USER, "Test",
                "Test", "test@test.test", "123");
        assertTrue(user instanceof ReaderUser);
    }

    @Test(expected = InvalidEmailException.class)
    public void ValidUserFactory_invalidEmail_throwInvalidEmailException() throws InvalidEmailException {
        User user = validUserFactory.getValidUser(UserType.REGULAR_USER, "Test",
                "Test", "test@@@@@test.test", "123");
    }
}
