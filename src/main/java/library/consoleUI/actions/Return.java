package library.consoleUI.actions;

import library.exception.BookNotFoundException;
import library.entity.book.Book;
import library.entity.user.User;
import library.util.file_managament.BookStorage;
import library.util.file_managament.UserStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import static library.consoleUI.text_formatter.Beautify.beautifyPrint;

public class Return {

    private BookStorage bookStorage = new BookStorage();
    private UserStorage userStorage = new UserStorage();
    private Scanner in = new Scanner(System.in);
    private User user;

    public Return(User user) {
        this.user = user;
    }

    public void returnBook() {
        beautifyPrint("borrowed books");

        Map<Integer, Book> bookMap = new HashMap<>();

        for (Book book : user.getBorrowedBooks()) {
            bookMap.put(user.getBorrowedBooks().indexOf(book), book);
            System.out.println("[" + user.getBorrowedBooks().indexOf(book) + "]" +
                    "Book: " + book.getTitle() +
                    " by " + book.getAuthorsName() + " " + book.getAuthorsSurname());
        }
        System.out.println("Which book would you like to return?");


        int index = 0;
        try {
            index = in.nextInt();
            if (!(index > -1 && index < bookMap.size())) {
                System.out.println("Wrong index.");
                return;
            }
        } catch (Exception e){
            System.out.println("Wrong index.");
            return;
        }
        Book book = bookMap.get(index);
        Book storageBook = null;
        try {
            storageBook = bookStorage.findBookByTitle(book.getTitle());
        } catch (BookNotFoundException e) {
            bookStorage.put(book);
        }


        Book borrowedBook = user.getBorrowedBooks().stream()
                .filter(b -> b.getAuthorsName().equals(book.getAuthorsName()) &&
                        b.getAuthorsSurname().equals(book.getAuthorsSurname()) &&
                        b.getGenre().equals(book.getGenre()) &&
                        b.getTitle().equals(book.getTitle()))
                .findFirst()
                .get();



        try {
            user.returnBorrowedBook(borrowedBook);
            bookStorage.put(storageBook);

        } catch (BookNotFoundException e) {
            bookStorage.put(book);
            return;
        }
        System.out.println("Book returned successfully.");
    }
}
