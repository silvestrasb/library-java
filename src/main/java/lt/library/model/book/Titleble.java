package lt.library.model.book;

import java.time.LocalDate;

public interface Releasable {
    
    String getTitle();

    String getGenre();

    LocalDate getReleaseDate();
    
}
