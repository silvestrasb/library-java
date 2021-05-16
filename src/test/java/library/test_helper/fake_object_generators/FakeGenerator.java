package library.test_helper.fake_object_generators;

import com.github.javafaker.Faker;
import library.exception.InvalidEmailException;
import library.entity.book.Book;
import library.entity.enums.UserType;
import library.entity.user.ReaderUser;
import library.service.factory.ValidUserFactory;

public class FakeGenerator {
    Faker faker = new Faker();

    public ReaderUser generateReaderUser() throws InvalidEmailException {

        ValidUserFactory validUserFactory = new ValidUserFactory();


        String name = faker.name().firstName();
        String surname = faker.name().lastName();
        String email = name.toLowerCase() + "." + surname.toLowerCase() + "@codeacademy.lt";
        String password = faker.dragonBall().character();

        ReaderUser readerUser = validUserFactory.getValidUser(UserType.REGULAR_USER, name, surname, email, password);

        for (int i = 0; i < faker.number().numberBetween(1, 5); i++) {
            readerUser.borrowBook(generateBook());
        }
        return readerUser;
    }

    public Book generateBook() {

        String title = faker.book().title();
        String authorsName = faker.name().firstName();
        String authorsSurname = faker.name().lastName();
        String genre = faker.book().genre();
        int quantity = faker.number().numberBetween(1, 100);

        return new Book(title, authorsName, authorsSurname, genre, quantity);
    }
}
