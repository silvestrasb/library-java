package library.entity;

import library.dto.request.CreateBookRequest;
import library.dto.request.UpdateBookRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "genre")
    private String genre;

    public Book(CreateBookRequest createBookRequest) {
        this.title = createBookRequest.getTitle();
        this.author = createBookRequest.getAuthor();
        this.genre = createBookRequest.getGenre();
    }

    public Book(Long id, UpdateBookRequest updateBookRequest){
        this.id = id;
        this.title = updateBookRequest.getTitle();
        this.author = updateBookRequest.getAuthor();
        this.genre = updateBookRequest.getGenre();
    }

}
