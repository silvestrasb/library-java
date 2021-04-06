package library.service.storage;

public interface Storage<E> {

    void put(E value);

    void update();

}
