package library.util.file_managament;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import library.exception.EmailAlreadyExistsException;
import library.exception.UserNotFoundException;
import library.model.user.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class UserStorage<E extends User> implements Storage<E> {

    private static final String USER_DATA_FILENAME = "user_data.json";

    private File userFile = new File(USER_DATA_FILENAME);

    private List<E> userList = new ArrayList<>();

    @Override
    public void put(E user) throws EmailAlreadyExistsException {
        duplicateEmailCheck(user.getEmail());
        userList.add(user);
        this.update();
    }

    @Override
    public void update() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writer().writeValue(userFile, userList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(E updateUser) {
        User userFromDataBase =  userList.stream()
                .filter(user -> user.getEmail().equals(updateUser.getEmail())
                        && user.getPassword().equals(updateUser.getPassword()))
                .findFirst().get();

        userList.remove(userFromDataBase);
        userList.add(updateUser);
        update();
    }

    public User get(String email, String password) throws UserNotFoundException {
        try {
            return userList.stream()
                    .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                    .findFirst().get();
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException();
        }
    }

    private void duplicateEmailCheck(String userEmail) throws EmailAlreadyExistsException {
        if (userList.stream()
                .anyMatch(user -> user.getEmail().equals(userEmail))) {
            throw new EmailAlreadyExistsException();
        }
    }
}
