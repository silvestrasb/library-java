package library.model.book;

public interface Quantifiable {
    int getQuantity();

    void removeOne();

    void addOne();

    void add(int quantity);

    void remove(int quantity);

    void setQuantity(int quantity);
}
