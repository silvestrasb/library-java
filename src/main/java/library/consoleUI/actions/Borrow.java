package library.consoleUI.actions;

import library.exception.AuthorDoesNotExistsException;
import library.exception.BookNotFoundException;
import library.entity.book.Book;
import library.entity.user.User;
import library.util.file_managament.BookStorage;
import library.util.file_managament.UserStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static library.consoleUI.text_formatter.Beautify.beautifyPrint;

public class Borrow {
    private Scanner in = new Scanner(System.in);
    private BookStorage bookStorage = new BookStorage();
    private UserStorage userStorage = new UserStorage();
    private User user;

    public Borrow(User user){
        this.user = user;
    }

    public void searchByAuthor() {
        beautifyPrint("search by author");
        System.out.print("Please enter authors name: ");
        String authorsInitials = in.next();
        in.nextLine();
        List<Book> booksByAuthor = null;
        try {
            booksByAuthor = bookStorage.findBookByAuthor(authorsInitials);
        } catch (AuthorDoesNotExistsException e) {
            System.out.println("Such author was not found.");
            return;
        }

        Map<Integer, Book> bookMap = new HashMap<>();

        for (Book book : booksByAuthor) {
            bookMap.put(booksByAuthor.indexOf(book), book);
            System.out.println("[" + booksByAuthor.indexOf(book) + "]"
                    + " Title : " + book.getTitle()
                    + " Genre : " + book.getGenre()
                    + " Quantity :" + book.getQuantity());
        }
        System.out.println("Borrow? [Y/N]");
        String rentStatus = in.next().toUpperCase();
        switch (rentStatus) {
            case "Y":
                int index = 0;
                if (bookMap.size() > 1) {
                    System.out.println("Which book would you like to borrow?");
                    try {
                        index = in.nextInt();
                        if (!(index > -1 && index < bookMap.size())) {
                            System.out.println("Wrong index.");
                            return;
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid Number");
                    }
                }

                Book book = bookMap.get(index);

                System.out.println("How many would you like to borrow?");
                int bookQuantity = 0;
                try {
                    bookQuantity = in.nextInt();
                    if (!(bookQuantity > 0 && bookQuantity <= book.getQuantity())) {
                        System.out.println("Invalid quantity.");
                        return;
                    }
                }catch (Exception e){
                    System.out.println("Invalid quantity.");
                    return;
                }

                book.setQuantity(bookQuantity);
                user.borrowBook(book);
                book.setQuantity(-bookQuantity);
                bookStorage.put(book);
                userStorage.updateUser(user);
                System.out.println("Happy reading, book borrowed successfully");
                break;
            default:
                break;
        }

    }

    public void searchByTitle() {
        beautifyPrint("search by title");
        System.out.print("Please enter book's title: ");
//        in.nextLine();
        String bookTitle = in.next();
//        in.nextLine();
        Book book = null;
        try {
            book = bookStorage.findBookByTitle(bookTitle);
        } catch (BookNotFoundException e) {
            System.out.println("Such Book Does Not Exist.");
            return;
        }

        System.out.println("Author : " + book.getAuthorsName()
                + " " + book.getAuthorsSurname()
                + " Genre : " + book.getGenre()
                + " Quantity :" + book.getQuantity());
        System.out.println("Borrow? [Y/N]");
        String rentStatus = in.next().toUpperCase();
        switch (rentStatus) {
            case "Y":
                System.out.println("How many would you like to borrow?");
                int numberOfBooks = in.nextInt();
                if (!(numberOfBooks > 0 && numberOfBooks <= book.getQuantity())) {
                    System.out.println("Invalid quantity.");
                    return;
                }

                book.setQuantity(numberOfBooks);
                user.borrowBook(book);
                userStorage.updateUser(user);


                int oldQuantity = book.getQuantity();
                book.setQuantity(numberOfBooks);
                user.borrowBook(book);
                book.setQuantity(oldQuantity - numberOfBooks);
                userStorage.updateUser(user);
                System.out.println("Happy reading, book borrowed successfully");
                break;
            default:
                break;
        }


    }

    public void userBorrowedBooks() {
        beautifyPrint("borrowed books");
        for (Book book : user.getBorrowedBooks()) {
            System.out.println("Quantity : " + book.getQuantity() +
                    ", Book: " + book.getTitle() +
                    " by " + book.getAuthorsName() + " " + book.getAuthorsSurname());
        }
    }
}
