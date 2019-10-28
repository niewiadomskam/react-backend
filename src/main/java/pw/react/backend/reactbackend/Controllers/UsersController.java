package pw.react.backend.reactbackend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.react.backend.reactbackend.Models.User;
import pw.react.backend.reactbackend.ManagementService.UsersManagementService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/spring-demo")
public class UsersController {
    private UsersManagementService _usersManagementService;

    @Autowired
    public UsersController(UsersManagementService usersManagementService) {
        _usersManagementService = usersManagementService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(@RequestParam(required = false) String login) throws Exception {
        List<User> result;
        if (login != null && login.length() > 0)
            result = _usersManagementService.findByLogin(login);
        else
            result = _usersManagementService.findAll();
        if (result == null) {
            throw new Exception("User not found, login: " + login);
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/users/")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) throws Exception {
        if (_usersManagementService.exists(user)) {
            throw new Exception("User already exists, login: " + user.GetLogin());
        }

        User result = _usersManagementService.save(user);

        return ResponseEntity.ok(result);
    }

}