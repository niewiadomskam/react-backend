package pw.react.backend.reactbackend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.react.backend.reactbackend.Models.User;
import pw.react.backend.reactbackend.Service.UsersService;
import pw.react.backend.reactbackend.Repositories.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Users")
public class UsersController {

    private final UsersService _usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        _usersService=usersService;
    }

    @PostMapping("/Users/")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user)  {
        if (_usersService.exists(user)) {
            throw new UserAlreadyExistsException("Login: " + user.getLogin());
        }
        User result = _usersService.save(user);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(_usersService.findAll());
    }

    @GetMapping("/Users/{id}")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") int id) {
        User result = _usersService.findById(id);
        if (result == null) {
            throw new UserNotFoundException("Id: " + id);
        }
        return ResponseEntity.ok(result);
    }
    @PutMapping("/Users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") int id, @Valid @RequestBody User user) {
        User userToUpdate = _usersService.findById(id);
        if (userToUpdate == null) {
            throw new UserNotFoundException("Id: " + id);
        }
        userToUpdate.setAllData(user.getLogin(), user.getFirstName(), user.getLastName(), user.getBirthDate(), user.getIsActive());
        final User updatedUser = _usersService.save(userToUpdate);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/Users/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable(value = "id") int id) {
        User userToDelete = _usersService.findById(id);
        if (userToDelete == null) {
            throw new UserNotFoundException("Id: " + id);
        }

        _usersService.delete(userToDelete);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return ResponseEntity.ok(response);
    }

    @ExceptionHandler({UserAlreadyExistsException.class})
    public ResponseEntity<ErrorResponse> alreadyExists(UserAlreadyExistsException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ErrorResponse> notFound(UserNotFoundException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value(), "The user was not found"), HttpStatus.NOT_FOUND);
    }
}