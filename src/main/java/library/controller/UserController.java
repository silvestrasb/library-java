package library.controller;

import library.dto.UserDTO;
import library.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public UserDTO getUser(@PathVariable("id") Long userId) {
        return new UserDTO(userService.getUser(userId));
    }

}
