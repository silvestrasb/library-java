package library.consoleUI.actions;

import library.entity.Book;
import library.entity.ReaderUser;
import library.exception.AuthorDoesNotExistsException;
import library.exception.BookNotFoundException;
import library.exception.ReaderUserNotFoundException;
import library.service.BookService;
import library.service.BorrowService;
import library.service.ReaderUserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static library.consoleUI.text_formatter.Beautify.beautifyPrint;

public class Borrow {
    private final Scanner in = new Scanner(System.in);
    private final BookService bookService = new BookService();
    private final ReaderUser user;
    private final BorrowService borrowService = new BorrowService();
    private final ReaderUserService readerUserService = new ReaderUserService();

    public Borrow(ReaderUser user) {
        this.user = user;
    }

    public void searchByGenre() {
        beautifyPrint("search by genre");
        System.out.print("Please enter the genre: ");
        String genre = in.nextLine();
        List<Book> booksByGenre;
        try {
            booksByGenre = bookService.findBooksByGenre(genre);
        } catch (Exception | BookNotFoundException e) {
            System.out.println("Such genre was not found.");
            return;
        }

        Map<Integer, Book> bookMap = new HashMap<>();

        for (Book book : booksByGenre) {
            bookMap.put(booksByGenre.indexOf(book), book);
            System.out.println("[" + booksByGenre.indexOf(book) + "]" +
                    "Book: " + book.getTitle() +
                    " by " + book.getAuthorsName() + " " + book.getAuthorsSurname());
        }
        System.out.print("Which book would you like to borrow?");

        int index;
        try {
            index = Integer.parseInt(in.nextLine());
            if (!(index > -1 && index < booksByGenre.size())) {
                System.out.println("Wrong index.");
                return;
            }
        } catch (Exception e) {
            System.out.println("Wrong index.");
            return;
        }

        Book book = bookMap.get(index);

        borrowService.borrowBook(book, user);

        System.out.println("Happy reading, book borrowed successfully");
    }

    public void searchByAuthor() {
        beautifyPrint("search by author");
        System.out.print("Please enter authors name: ");
        String authorsName = in.next();
        System.out.print("Please enter authors surname: ");
        String authorsSurname = in.next();
        in.nextLine();
        List<Book> booksByAuthor;
        try {
            booksByAuthor = bookService.findBookByAuthor(authorsName, authorsSurname);
        } catch (Exception | AuthorDoesNotExistsException e) {
            System.out.println("Such author was not found.");
            return;
        }

        Map<Integer, Book> bookMap = new HashMap<>();

        for (Book book : booksByAuthor) {
            bookMap.put(booksByAuthor.indexOf(book), book);
            System.out.println("[" + booksByAuthor.indexOf(book) + "]" +
                    "Book: " + book.getTitle() +
                    " by " + book.getAuthorsName() + " " + book.getAuthorsSurname());
        }
        System.out.print("Which book would you like to borrow?");

        int index;
        try {
            index = Integer.parseInt(in.nextLine());
            if (!(index > -1 && index < booksByAuthor.size())) {
                System.out.println("Wrong index.");
                return;
            }
        } catch (Exception e) {
            System.out.println("Wrong index.");
            return;
        }

        Book book = bookMap.get(index);

        borrowService.borrowBook(book, user);

        System.out.println("Happy reading, book borrowed successfully");
    }

    public void searchByTitle() {
        beautifyPrint("search by title");
        System.out.print("Please enter book's title: ");
        String bookTitle = in.nextLine();
        Book book;
        try {
            book = bookService.findBookByTitle(bookTitle);
        } catch (BookNotFoundException e) {
            System.out.println("Such Book Does Not Exist.");
            return;
        }
        System.out.println(book);

        System.out.println("Borrow? [Y/N]");
        if (in.next().equalsIgnoreCase("Y")) {
            borrowService.borrowBook(book, user);
            System.out.println("Happy reading, book borrowed successfully");
        }
    }

    public void searchById() {
        beautifyPrint("all books");
        try {
            bookService.getBookList().forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("No books are available.");
        }
        beautifyPrint("You'd you like to borrow one?[Y/N]");
        if (in.next().equalsIgnoreCase("y")) {
            System.out.print("Select the index: ");
            try {
                int id = in.nextInt();
                borrowService.borrowBook(bookService.getById(id), user);
                System.out.println("Book borrowed successfully.");

            } catch (Exception e) {
                System.out.println("Something went wrong...");
            }
        }
    }

    public void userBorrowedBooks() throws ReaderUserNotFoundException {
        beautifyPrint("borrowed books");
        borrowService.listReaderUserBooks(user.getId()).forEach(System.out::println);
    }
}
