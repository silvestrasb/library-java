package library.service.factory;

import library.exception.InvalidEmailException;
import library.entity.enums.UserType;
import library.entity.user.ReaderUser;
import library.entity.user.User;
import library.util.validators.EmailValidator;

public class ValidUserFactory {
    public <E extends User> E getValidUser(UserType userType, String name,
                                           String surname, String email, String password) throws InvalidEmailException {
        EmailValidator.validEmail(email);
        switch (userType){
            case REGULAR_USER:
                return (E) new ReaderUser(name, surname, email, password);
            default:
                throw new IllegalStateException("Unexpected value: " + userType);
        }
    }
}
