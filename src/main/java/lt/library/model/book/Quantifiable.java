package lt.library.model.book;

public interface Quantifiable {
    int getQuantity();

    void removeOne();

    void addOne();
}
