package library;

import library.exception.*;
import library.model.book.Book;
import library.model.enums.UserType;
import library.model.user.ReaderUser;
import library.service.factory.ValidUserFactory;
import library.util.file_managament.BookStorage;
import library.util.file_managament.UserStorage;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final Scanner in = new Scanner(System.in);
    private static final BookStorage bookStorage = new BookStorage();
    private static final UserStorage<ReaderUser> userStorage = new UserStorage<>();
    private static final ValidUserFactory validUserFactory = new ValidUserFactory();
    private static ReaderUser user = null;
    private static UserStates userState = UserStates.LOGGED_OUT;

    public static void main(String[] args) {
        FakeGenerator fakeGenerator = new FakeGenerator();
        for (int i = 0; i < 20; i++) {
            bookStorage.put(fakeGenerator.generateBook());
        }
        welcomeMessage();
        String command;
        startMenu();
        ReaderUser userAdmin = new ReaderUser("admin", "admin", "admin@admin.com", "123");
        userStorage.put(userAdmin);
        while (userState == UserStates.LOGGED_OUT) {
            beautifyPrint("start menu");
            command = in.next();
            switch (command) {
                case "-l":
                    logIn();
                    break;
                case "-r":
                    register();
                    break;
                case "-lo":
                    logOut();
                    break;
                case "-c":
                    startMenu();
                    break;
            }
            while (userState == UserStates.LOGGED_IN) {
                beautifyPrint("logged in menu");
                command = in.next();
                switch (command) {
                    case "-lo":
                        logOut();
                        break;
                    case "-c":
                        logInMenu();
                        break;
                    case "-sa":
                        searchByAuthor();
                        break;
                    case "-st":
                        searchByTitle();
                        break;
                    case "-bb":
                        userBorrowedBooks();
                        break;
                    case "-rb":
                        returnBook();
                        break;
                }
            }

        }


    }

    private static void logInMenu() {
        beautifyPrint("log in menu");
        beautifyPrint("Type the following commands");
        System.out.println("-lo Log out.");
        System.out.println("-sa Search book by the author.");
        System.out.println("-st Search by the title.");
        System.out.println("-bb See your borrowed books.");
        System.out.println("-rb Return book.");
        System.out.println("-c See list of commands.");
    }

    private static void searchByAuthor() {
        beautifyPrint("search by author");
        System.out.print("Please enter authors name or surname or both: ");
        String authorsInitials = in.next();
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
        System.out.println("Rent? [Y/N]");
        String rentStatus = in.next().toUpperCase();
        switch (rentStatus) {
            case "Y":
                System.out.println("Which book would you like to borrow?");
                int index = in.nextInt();

                if (!(index > -1 && index < bookMap.size())){
                    System.out.println("Wrong index.");
                    return;
                }

                Book book = bookMap.get(index);

                System.out.println("How many would you like to borrow?");
                int bookQuantity = in.nextInt();

                if (!(bookQuantity > 0 && bookQuantity <= book.getQuantity())) {
                    System.out.println("Invalid quantity.");
                    return;
                }

                book.setQuantity(bookQuantity);
                user.borrowBook(book);
                userStorage.updateUser(user);
                bookStorage.borrowBook(book);
                System.out.println("Happy reading, book borrowed successfully");
                break;
            default:
                break;
        }

    }

    private static void searchByTitle() {
        beautifyPrint("search by title");
        System.out.print("Please enter book's title: ");
        in.nextLine();
        String bookTitle = in.nextLine();
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
        System.out.println("Rent? [Y/N]");
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
                bookStorage.borrowBook(book);
                user.borrowBook(book);
                userStorage.updateUser(user);
                System.out.println("Happy reading, book borrowed successfully");
                break;
            default:
                break;
        }


    }

    private static void userBorrowedBooks() {
        beautifyPrint("borrowed books");
        for (Book book : user.getBorrowedBooks()) {
            System.out.println("Quantity : " + book.getQuantity() +
                    ", Book: " + book.getTitle() +
                    " by " + book.getAuthorsName() + " " + book.getAuthorsSurname());
        }
    }

    private static void returnBook() {
        beautifyPrint("borrowed books");
        Map<Integer, Book> borrowedBooks = new HashMap<>();
        for (Book book : user.getBorrowedBooks()) {
            borrowedBooks.put(user.getBorrowedBooks().indexOf(book), book);
            System.out.println("[" + user.getBorrowedBooks().indexOf(book) + "]" +
                    "Quantity : " + book.getQuantity() +
                    ", Book: " + book.getTitle() +
                    " by " + book.getAuthorsName() + " " + book.getAuthorsSurname());
        }

        System.out.println("Which book would you like to return?");

        int index = in.nextInt();
        if (!(index > -1 && index < borrowedBooks.size())){
            System.out.println("Wrong index.");
            return;
        }
        Book book = borrowedBooks.get(index);
        System.out.println("How many would you like to return?");
        int numberOfBooks = in.nextInt();
        if (!(numberOfBooks > 0 && numberOfBooks <= book.getQuantity())) {
            System.out.println("Invalid quantity.");
            return;
        }
        book.setQuantity(numberOfBooks);
        try {
            user.returnBorrowedBook(book);
        } catch (BookNotFoundException e) {
            beautifyPrint("book was not found");
            return;
        }
        userStorage.updateUser(user);
        bookStorage.returnBook(book);
        System.out.println("Book return successfully!");

    }

    private static void startMenu() {
        beautifyPrint("Type the following commands");
        System.out.println("-l Log In");
        System.out.println("-r Register");
        System.out.println("-lo Log out");
    }


    private static void logOut() {
        beautifyPrint("log out");
        if (user == null) {
            System.out.println("Please log in first.");
            return;
        }
        user = null;
        System.out.println("You've been successfully logged out");
        userState = UserStates.LOGGED_OUT;
        startMenu();
    }

    private static void logIn() {
        beautifyPrint("operation log-in");
        if (user != null) {
            System.out.println("Please log out first.");
            return;
        }
        System.out.print("Please enter your email: ");
        String email = in.next();
        System.out.print("Please enter your password: ");
        String password = DigestUtils.sha256Hex(in.next());
        String[] userDetails = {email, password};
        try {
            user = (ReaderUser) userStorage.get(email, password);
        } catch (UserNotFoundException e) {
            errorMessage("user not found");
            return;
        }
        System.out.println("Welcome " + user.getName() + " " + user.getSurname() + "!");
        userState = UserStates.LOGGED_IN;
        logInMenu();
    }

    private static void register() {
        beautifyPrint("registration");
        if (user != null) {
            System.out.println("Please log out first.");
            return;
        }
        System.out.print("Enter your name: ");
        String name = in.next();
        System.out.print("Enter your surname: ");
        String surname = in.next();
        System.out.print("Enter your email: ");
        String email = in.next();
        System.out.print("Enter your password: ");
        String password = in.next();
        try {
            user = validUserFactory.getValidUser(UserType.REGULAR_USER, name, surname, email, password);
        } catch (InvalidEmailException e) {
            System.out.println("Invalid email");
            return;
        }
        try {
            userStorage.put(user);
        } catch (EmailAlreadyExistsException e) {
            System.out.println("Email already exists");
            return;
        }
        System.out.println("Welcome " + user.getName() + "!");
        userState = UserStates.LOGGED_IN;
        logInMenu();
    }


    private static void errorMessage(String message) {
        beautifyPrint("");
        beautifyPrint("error");
        beautifyPrint(message);
    }


    private static void welcomeMessage() {
        beautifyPrint("");
        beautifyPrint("Welcome to JAVA Library!");
    }


    private static void beautifyPrint(String message) {
        if (message.length() < 2) {
            System.out.println("-".repeat(62));
        } else {

            int size = message.length();

            double minusesFromSides = (double) (60 - size) / 2;

            int minusesFromTheLeft = (int) Math.floor(minusesFromSides);
            int minusesFromTheRight = (int) Math.ceil(minusesFromSides);
            String beautifiedText = "-".repeat(minusesFromTheLeft) + " " + message.toUpperCase() + " "
                    + "-".repeat(minusesFromTheRight);
            System.out.println(beautifiedText);
        }
    }

    enum UserStates {
        LOGGED_IN,
        LOGGED_OUT
    }
}
