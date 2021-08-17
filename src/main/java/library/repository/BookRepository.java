package library.repository;

import library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(
            "FROM Book b WHERE " +
            "UPPER(b.title) LIKE UPPER(CONCAT('%', :title, '%'))" +
            " AND " +
            "UPPER(b.author) LIKE UPPER(CONCAT('%', :author, '%'))" +
            " AND " +
            "UPPER(b.genre) LIKE UPPER(CONCAT('%', :genre, '%'))"
    )
    List<Book> findByFields(String title, String author, String genre);

}
