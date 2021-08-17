package library.dto.response;

import library.entity.Book;
import lombok.Data;

@Data
public class UpdateBookResponse {
    private Long id;

    private String title;

    private String author;

    private String genre;

    public UpdateBookResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.genre = book.getGenre();
    }
}
