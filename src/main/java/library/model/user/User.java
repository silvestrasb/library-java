package library.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import library.exception.BookNotFoundException;
import library.model.book.Book;
import library.util.collapser.BookListCollapser;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = WriterUser.class, name = "WriterUser"),
        @JsonSubTypes.Type(value = ReaderUser.class, name = "ReaderUser")}
)

@Getter
public abstract class User implements Borrow {

    @JsonProperty("type")
    @FieldNameConstants.Exclude
    private String type = this.getClass().getSimpleName(); // Needed for json polymorphic deserialization
    private String name;
    private String surname;
    private String email;
    private String password;
    private List<Book> borrowedBooks = new ArrayList<>();

    public User() {
    }

    public User(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = DigestUtils.sha256Hex(password);
    }

}
