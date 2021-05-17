package library.consoleUI.actions;

import library.entity.Book;
import library.entity.ReaderUser;
import library.exception.BookNotFoundException;
import library.exception.ReaderUserNotFoundException;
import library.service.BorrowService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static library.consoleUI.text_formatter.Beautify.beautifyPrint;

public class Return {

    private final Scanner in = new Scanner(System.in);
    private final BorrowService borrowService = new BorrowService();
    private final ReaderUser user;

    public Return(ReaderUser user) {
        this.user = user;
    }

    public void returnBook() throws ReaderUserNotFoundException, BookNotFoundException {
        beautifyPrint("borrowed books");

        Map<Integer, Book> bookMap = new HashMap<>();

        List<Book> userBooks = borrowService.listReaderUserBooks(user.getId());

        for (Book book : userBooks) {
            bookMap.put(userBooks.indexOf(book), book);
            System.out.println("[" + userBooks.indexOf(book) + "]" +
                    "Book: " + book.getTitle() +
                    " by " + book.getAuthorsName() + " " + book.getAuthorsSurname());
        }
        System.out.println("Which book would you like to return?");

        int index;
        try {
            index = Integer.parseInt(in.nextLine());
            if (!(index > -1 && index < bookMap.size())) {
                System.out.println("Wrong index.");
                return;
            }
        } catch (Exception e) {
            System.out.println("Wrong index.");
            return;
        }
        Book book = bookMap.get(index);

        borrowService.returnBook(book);

        System.out.println("Book returned successfully.");
    }
}
