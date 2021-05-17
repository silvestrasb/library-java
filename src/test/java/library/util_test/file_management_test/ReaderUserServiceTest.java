package library.util_test.file_management_test;

import library.HibernateConfig;
import library.entity.ReaderUser;
import library.exception.InvalidEmailException;
import library.exception.ReaderUserNotFoundException;
import library.test_helper.fake_object_generators.FakeGenerator;
import library.service.ReaderUserService;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;

import static org.junit.Assert.assertEquals;

public class ReaderUserServiceTest {


    private final FakeGenerator fakeGenerator = new FakeGenerator();
    private final ReaderUserService readerUserService = new ReaderUserService();

    @AfterAll
    static void afterAll() {
        HibernateConfig.closeSessionFactory();
    }

    @Test
    public void get_PutCorrectReaderUserData_GetCorrespondingReaderUser() throws InvalidEmailException, ReaderUserNotFoundException {

        // Given
        HibernateConfig.buildSessionFactory();


        ReaderUser user1 = fakeGenerator.generateReaderUser();
        String user1Email = user1.getEmail();
        String user1Password = user1.getPassword();

        ReaderUser user2 = fakeGenerator.generateReaderUser();
        String user2Email = user2.getEmail();
        String user2Password = user2.getPassword();

        ReaderUser user3 = fakeGenerator.generateReaderUser();
        String user3Email = user3.getEmail();
        String user3Password = user3.getPassword();

        // When

        readerUserService.save(user1);
        readerUserService.save(user2);
        readerUserService.save(user3);


        // Then

        ReaderUser fetchedReaderUser1 = readerUserService.get(user1Email, user1Password);
        assertEquals(user1.getEmail(), fetchedReaderUser1.getEmail());
        ReaderUser fetchedReaderUser2 = readerUserService.get(user2Email, user2Password);
        assertEquals(user2.getEmail(), fetchedReaderUser2.getEmail());
        ReaderUser fetchedReaderUser3 = readerUserService.get(user3Email, user3Password);
        assertEquals(user3.getEmail(), fetchedReaderUser3.getEmail());

        readerUserService.delete(user1);
        readerUserService.delete(user2);
        readerUserService.delete(user3);
    }

    @Test(expected = ReaderUserNotFoundException.class)
    public void get_PutIncorrectEmail_ThrowReaderUserNotFoundException() throws ReaderUserNotFoundException, InvalidEmailException {
        // Given
        HibernateConfig.buildSessionFactory();

        ReaderUser user = fakeGenerator.generateReaderUser();

        String userPassword = user.getPassword();
        String incorrectEmail = "test.test.test@notcodeacademy.lt";

        // When

        readerUserService.save(user);
        try {
            ReaderUser fetchedReaderUser1 = readerUserService.get(incorrectEmail, userPassword);
        } finally {
            readerUserService.delete(user);
        }

        // Then
        /*
        ReaderUserNotFoundException should be thrown
        */

    }

    @Test(expected = ReaderUserNotFoundException.class)
    public void get_PutIncorrectPassword_ThrowReaderUserNotFoundException() throws ReaderUserNotFoundException, InvalidEmailException {
        // Given
        HibernateConfig.buildSessionFactory();

        ReaderUser user = fakeGenerator.generateReaderUser();

        String userEmail = user.getEmail();
        String incorrectPassword = ".'.'.'.'.'.incorrect password.'.'.'.'.'.";

        // When

        readerUserService.save(user);
        try {
            ReaderUser fetchedReaderUser1 = readerUserService.get(userEmail, incorrectPassword);
        } finally {
            readerUserService.delete(user);
        }

        // Then

        /*
        ReaderUserNotFoundException should be thrown
        */

    }
}
