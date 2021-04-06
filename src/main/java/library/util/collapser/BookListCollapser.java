package library.util.collapser;

import library.model.book.Book;

import java.util.*;
import java.util.stream.Collectors;

public class BookListCollapser {

    public List<Book> collapseBooks(List<Book> bookList) {

        Set<Book> collapsedSet = new TreeSet<>((o1, o2) -> {
            if(o1.getAuthorsName().equals(o2.getAuthorsName()) &&
                    o1.getAuthorsSurname().equals(o2.getAuthorsSurname()) &&
                    o1.getGenre().equals(o2.getGenre()) &&
                    o1.getTitle().equals(o2.getTitle()) &&
                    o1.getQuantity() == o2.getQuantity()
            ) return 0;
            return 1;
        });

        for (Book book : bookList){
            List<Book> duplicates = bookList.stream()
                    .filter(b -> b.getAuthorsName().equals(book.getAuthorsName()) &&
                            b.getAuthorsSurname().equals(book.getAuthorsSurname()) &&
                            b.getGenre().equals(book.getGenre()) &&
                            b.getTitle().equals(book.getTitle()))
                    .collect(Collectors.toList());

            Book testBook = duplicates.get(0);
            String authorsName = testBook.getAuthorsName();
            String authorsSurname = testBook.getAuthorsSurname();
            String genre = testBook.getGenre();
            String title = testBook.getTitle();

            Book bookTest = new Book(title, authorsName,authorsSurname,genre, duplicates.stream()
                    .mapToInt(Book::getQuantity).sum());

            collapsedSet.add(bookTest);
        }

        return new ArrayList<>(collapsedSet);
    }
}
