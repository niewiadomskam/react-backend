package pw.react.backend.reactbackend.Service;

import  pw.react.backend.reactbackend.Models.User;

import java.util.List;

public interface IUsersService {
    User findById(int id);
    List<User> findAll();
    List<User> findByLogin(String login);
    User save(User user);
    User updateUser(User user);
    void delete(User user);
    boolean exists(User user);
}