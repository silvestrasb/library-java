package library.model.book;

public class Book {

    private String title;
    private String authorsName;
    private String authorsSurname;
    private String genre;

    @Override
    public String getTitle() {
        return this.title;
    }

    public String getAuthorsName() {
        return this.authorsName;
    }

    public String getAuthorsSurname() {
        return this.authorsSurname;
    }

    @Override
    public String getGenre() {
        return this.genre;
    }

}
