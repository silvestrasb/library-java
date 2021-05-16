package library.model.user;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
//@JsonTypeInfo(
//        use = JsonTypeInfo.Id.NAME,
//        include = JsonTypeInfo.As.PROPERTY,
//        property = "@class",
//        visible = true)
@JsonTypeName("WriterUser")
public class WriterUser extends User {

    private String test;
    public WriterUser(){}

    public String getTest() {
        return test;
    }

    public WriterUser(String name, String surname, String email, String password, String test) {
        super(name, surname, email, password);
        this.test = test;
    }



}
