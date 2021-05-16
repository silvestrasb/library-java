package library.entity.book;

public class Book implements Quantifiable, Releasable {

    private String title;
    private String authorsName;
    private String authorsSurname;
    private String genre;
    private int quantity;

    public Book() {
    }

    public Book(String title, String authorsName, String authorsSurname, String genre, int quantity) {
        this.title = title;
        this.authorsName = authorsName;
        this.authorsSurname = authorsSurname;
        this.genre = genre;
        this.quantity = quantity;
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
                ", genre='" + genre + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public int getQuantity() {
        return this.quantity;
    }

    @Override
    public void removeOne() {
        this.quantity--;
    }

    @Override
    public void addOne() {
        this.quantity++;
    }

    @Override
    public void add(int quantity) {
        this.quantity += quantity;
    }

    @Override
    public void remove(int quantity) {
        this.quantity -= quantity;
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
