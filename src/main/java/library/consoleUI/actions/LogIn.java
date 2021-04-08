package library.consoleUI.actions;

import library.exception.EmailAlreadyExistsException;
import library.exception.InvalidEmailException;
import library.exception.UserNotFoundException;
import library.model.enums.UserType;
import library.model.user.User;
import library.service.factory.ValidUserFactory;
import library.util.file_managament.UserStorage;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Scanner;

import static library.consoleUI.text_formatter.Beautify.beautifyPrint;

public class LogIn {

    private Scanner in = new Scanner(System.in);
    private UserStorage userStorage = new UserStorage<>();
    private User user = null;
    private ValidUserFactory validUserFactory = new ValidUserFactory();

    public User start() {
        beautifyPrint("log in menu");
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
            if (user == null){
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
        String password = DigestUtils.sha256Hex(in.next());

        try {
            user = userStorage.get(email, password);
        } catch (UserNotFoundException e) {
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
            user = validUserFactory.getValidUser(UserType.REGULAR_USER, name, surname, email, password);
            userStorage.put(user);
        } catch (InvalidEmailException e) {
            System.out.println("Invalid email");
            return;
        } catch (EmailAlreadyExistsException e) {
            System.out.println("Email already exists");
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
