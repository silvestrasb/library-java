package library.util_test;

import library.exception.InvalidEmailException;
import library.util.validators.EmailValidator;
import org.junit.Test;

public class EmailValidatorTest {

    @Test(expected = InvalidEmailException.class)
    public void validEmail_InvalidEmail_ThrowInvalidEmailException() throws InvalidEmailException {
        EmailValidator.validEmail("test@@@@@.com");
    }

    @Test
    public void validEmail_ValidEmail_DoesNothing() throws InvalidEmailException {
        EmailValidator.validEmail("tets@test.com");
    }
}
