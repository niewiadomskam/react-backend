package pw.react.backend.reactbackend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.react.backend.reactbackend.Models.User;
import pw.react.backend.reactbackend.Service.UsersService;
import pw.react.backend.reactbackend.Repositories.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/Users")
public class UsersController {

    private final UsersService _usersService;
    private final UsersRepository _usersRepository;

    @Autowired
    public UsersController(UsersService usersService, UsersRepository usersRepository) {
        _usersService=usersService;
        _usersRepository=usersRepository;
    }

    @PostMapping("/Users/")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user)  {
        User result = _usersRepository.save(user);

        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(_usersRepository.findAll());
    }

}