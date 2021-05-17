package library;

import library.consoleUI.actions.Borrow;
import library.consoleUI.actions.LogIn;
import library.consoleUI.actions.Return;
import library.entity.ReaderUser;
import library.exception.BookNotFoundException;
import library.exception.ReaderUserNotFoundException;
import library.service.BookService;
import library.service.ReaderUserService;

import java.util.Scanner;

import static library.consoleUI.text_formatter.Beautify.beautifyPrint;

public class Main {
    /*

    LogIn login = new LogIn();
    switch(this and that):
    case

     */

    private static final BookService bookStorage = new BookService();
    private static final ReaderUserService readerUserService = new ReaderUserService();
    private static Scanner in = new Scanner(System.in);
    private static ReaderUser user = null;


    public static void main(String[] args) throws ReaderUserNotFoundException, BookNotFoundException {


        HibernateConfig.buildSessionFactory();
        boolean whileCondition = true;
        while (whileCondition) {
            LogIn logIn = new LogIn();

            user = logIn.start();

            Borrow borrow = new Borrow(user);
            Return userReturn = new Return(user);

            beautifyPrint("Log In Menu");
            logInMenu();
            while (whileCondition) {
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
                    case "-sg":
                        borrow.searchByGenre();
                        break;
                    case "-ab":
                        borrow.searchById();
                        break;
                    case "-q":
                        whileCondition = false;
                        return;
                    case "-st":
                        borrow.searchByTitle();
                        break;
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
        }
        HibernateConfig.closeSessionFactory();
    }

    private static void logInMenu() {
        beautifyPrint("Type the following commands");
        System.out.println("-lo Log out.");
        System.out.println("-sa Search book by the author.");
        System.out.println("-st Search book by title.");
        System.out.println("-sg Search book by genre.");
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