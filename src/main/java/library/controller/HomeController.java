package library.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import library.dto.UserDTO;
import library.dto.request.RegisterRequest;
import library.dto.request.UpdateBookRequest;
import library.dto.response.LoginResponse;
import library.dto.response.UpdateBookResponse;
import library.entity.Book;
import library.entity.User;
import library.service.JwtService;
import library.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class HomeController {

    private final UserService userService;

    private final JwtService jwtService;

    public HomeController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public String home() {
        return "OK";
    }

    @GetMapping("/http")
    public ResponseEntity<Integer> http(@RequestParam("status") Integer status) {
        return new ResponseEntity<>(status, HttpStatus.valueOf(status));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO register(@Valid @RequestBody RegisterRequest registerRequest) {
        return new UserDTO(userService.createUser(new User(registerRequest)));
    }

    @PostMapping("/login")
    public LoginResponse login(@AuthenticationPrincipal User user) {
        return new LoginResponse(jwtService.createToken(user), new UserDTO(user));
    }

}
