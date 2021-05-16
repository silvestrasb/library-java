package library;

import library.consoleUI.actions.Borrow;
import library.consoleUI.actions.LogIn;
import library.consoleUI.actions.Return;
import library.consoleUI.book_data.PrintBooks;
import library.entity.user.User;
import library.service.factory.ValidUserFactory;
import library.util.file_managament.BookStorage;
import library.util.file_managament.UserStorage;

import java.util.Scanner;

import static library.consoleUI.text_formatter.Beautify.beautifyPrint;

public class Main {
    /*

    LogIn login = new LogIn();
    switch(this and that):
    case

     */

    private static final BookStorage bookStorage = new BookStorage();
    private static final UserStorage userStorage = new UserStorage();
    private static final ValidUserFactory validUserFactory = new ValidUserFactory();
    private static final PrintBooks printBooks = new PrintBooks();
    private static Scanner in = new Scanner(System.in);
    private static User user = null;


    public static void main(String[] args) {

        LogIn logIn = new LogIn();
        user = logIn.start();
        Borrow borrow = new Borrow(user);
        Return userReturn = new Return(user);


        beautifyPrint("Log In Menu");
        logInMenu();

        while (true) {
            String command = in.next();
            switch (command) {
                case "-lo":
                    logOut();
                    break;
                case "-sa":
                    borrow.searchByAuthor();
                    break;
                case "-bb":
                    borrow.userBorrowedBooks();
                    break;
                case "-rb":
                    userReturn.returnBook();
                    break;
                case "-ab":
                    printBooks.printBooks();
                    break;
                case "-q":
                    return;
                case "-c":
                    logInMenu();
                    break;
            }
            if (user != null) {
                beautifyPrint("Log In Menu");
            } else {
                break;
            }
        }
        main(new String[]{});
    }

    private static void logInMenu() {
        beautifyPrint("Type the following commands");
        System.out.println("-lo Log out.");
        System.out.println("-sa Search book by the author.");
        System.out.println("-bb See your borrowed books.");
        System.out.println("-rb Return book.");
        System.out.println("-ab See all books available.");
        System.out.println("-q Exit the application.");
        System.out.println("-c See list of commands.");
    }


    private static void logOut() {
        beautifyPrint("log out");
        user = null;
        System.out.println("You've been successfully logged out");
    }

}