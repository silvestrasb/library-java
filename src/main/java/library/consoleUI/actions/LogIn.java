package library.consoleUI.actions;

import library.entity.ReaderUser;
import library.exception.EmailAlreadyExistsException;
import library.exception.InvalidEmailException;
import library.exception.ReaderUserNotFoundException;
import library.service.ReaderUserService;

import java.util.Scanner;

import static library.consoleUI.text_formatter.Beautify.beautifyPrint;

public class LogIn {

    private final Scanner in = new Scanner(System.in);
    private final ReaderUserService readerUserService = new ReaderUserService();
    private ReaderUser user = null;

    public ReaderUser start() {
        beautifyPrint("register menu");
        commands();
        while (user == null) {
            String command = in.next();
            switch (command) {
                case "-l":
                    logIn();
                    break;
                case "-r":
                    register();
                    break;
                case "-c":
                    commands();
                    break;
                default:
                    System.out.println("Invalid Command. Type -c to see all commands");
                    break;
            }
            if (user == null) {
                beautifyPrint("log in menu");
            }
        }
        return user;
    }

    private void logIn() {
        beautifyPrint("log-in");
        System.out.print("Please enter your email: ");
        String email = in.next();

        System.out.print("Please enter your password: ");
        String password = in.next();

        try {
            user = readerUserService.get(email, password);
        } catch (ReaderUserNotFoundException e) {
            System.out.println("User not found!");
            return;
        }
        System.out.println("Welcome " + user.getName() + " " + user.getSurname() + "!");
    }

    private void register() {
        beautifyPrint("registration");

        System.out.print("Enter your name: ");
        String name = in.next();

        System.out.print("Enter your surname: ");
        String surname = in.next();

        System.out.print("Enter your email: ");
        String email = in.next();

        System.out.print("Enter your password: ");
        String password = in.next();

        try {
            user = new ReaderUser(name, surname, email, password);
            readerUserService.save(user);
        } catch (InvalidEmailException e) {
            System.out.println("Invalid email");
            user = null;
            return;
        } catch (EmailAlreadyExistsException e) {
            System.out.println("Email already exists");
            user = null;
            return;
        }
        System.out.println("Welcome " + user.getName() + "!");
    }

    private void commands() {
        beautifyPrint("Type the following commands");
        System.out.println("-l Log In.");
        System.out.println("-r Register.");
        System.out.println("-c See list of commands.");
    }
}
