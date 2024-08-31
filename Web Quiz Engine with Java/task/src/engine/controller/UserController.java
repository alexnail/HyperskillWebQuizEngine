package engine.controller;

import engine.exception.NonUniqueUserException;
import engine.model.UserDTO;
import engine.service.UserDetailsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserDetailsServiceImpl userDetailsService;

    public UserController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity createUser(@RequestBody @Valid UserDTO user) {
        userDetailsService.save(user);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(NonUniqueUserException.class)
    public ResponseEntity handleNonUniqueEmail() {
        return ResponseEntity.badRequest().build();
    }

}
