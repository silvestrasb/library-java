package library.util.file_managament;

public interface Storage<E> {

    void put(E value);

    void update();
}
