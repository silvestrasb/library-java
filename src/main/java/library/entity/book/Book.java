package library.entity.book;

public class Book implements Releasable {

    private String title;
    private String authorsName;
    private String authorsSurname;
    private String genre;

    public Book() {
    }

    public Book(String title, String authorsName, String authorsSurname, String genre) {
        this.title = title;
        this.authorsName = authorsName;
        this.authorsSurname = authorsSurname;
        this.genre = genre;
    }

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


    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", authorsName='" + authorsName + '\'' +
                ", authorsSurname='" + authorsSurname + '\'' +
                ", genre='" + genre + '\'' + "}";
    }


}
