package library.controller;

import library.dto.UserDTO;
import library.dto.request.RegisterRequest;
import library.dto.response.LoginResponse;
import library.entity.User;
import library.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String home() {
        return "OK";
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO register(@Valid @RequestBody RegisterRequest registerRequest) {
        return new UserDTO(userService.createUser(new User(registerRequest)));
    }



    @PostMapping("/login")
    public LoginResponse login(User user) {
        return new LoginResponse();
    }


}
