package library.model.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Lombok annotations
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private String title;
    private String authorsName;
    private String authorsSurname;
    private String genre;
}
