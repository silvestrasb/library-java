package lt.library.model.book;

import java.time.LocalDate;

public class Book implements Quantifiable, Releasable {

    private final String title;
    private final String authorsName;
    private final String authorsSurname;
    private final String genre;
    private final LocalDate releaseDate;
    private int quantity;

    public Book(String title, String authorsName, String authorsSurname, String genre, LocalDate releaseDate, int quantity) {
        this.title = title;
        this.authorsName = authorsName;
        this.authorsSurname = authorsSurname;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.quantity = quantity;
    }

    @Override
    public String getTitle() {
        return this.title;
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
                ", gendre='" + genre + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public LocalDate getReleaseDate() {
        return this.releaseDate;
    }

    @Override
    public int getQuantity() {
        return this.quantity;
    }

    @Override
    public void removeOne() {
        this.quantity --;
    }

    @Override
    public void addOne() {
        this.quantity ++;
    }

    @Override
    public void add(int quantity) {
        this.quantity += quantity;
    }

}
