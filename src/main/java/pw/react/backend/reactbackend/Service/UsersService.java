package pw.react.backend.reactbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.react.backend.reactbackend.Models.User;
import pw.react.backend.reactbackend.Repositories.*;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService implements IUsersService {

    private UsersRepository _usersRepository;

    @Autowired
    UsersService(UsersRepository usersRepository) {
        this._usersRepository = usersRepository;
    }
    @Override
    public User findById(int id) {
        return _usersRepository.findById(id);
    }
    @Override
    public List<User> findAll() {
        return _usersRepository.findAll();
    }
    @Override
    public List<User> findByLogin(String login) {
        return _usersRepository.findByLogin(login);
    }
    @Override
    public User save(User user) {
        return _usersRepository.save(user);
    }
    @Override
    public User updateUser(User user) {
        Optional<User> existingUser = _usersRepository.findById(String.valueOf(user.getId()));
        if (existingUser.isPresent()) {
            return _usersRepository.save(user);
        }
        else
            return  null;
    }
    @Override
    public void delete(User user) {
        _usersRepository.delete(user);
    }
    @Override
    public boolean exists(User user) {
        List<User> result = _usersRepository.findByLogin(user.getLogin());
        return result != null && !result.isEmpty();
    }
}