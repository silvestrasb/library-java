package library.entity.user;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("ReaderUser")
public class ReaderUser extends User {

    public ReaderUser() {
    }

    public ReaderUser(String name, String surname, String email, String password) {
        super(name, surname, email, password);
    }

}
