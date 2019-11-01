package pw.react.backend.reactbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.react.backend.reactbackend.Models.User;
import pw.react.backend.reactbackend.Repositories.*;

import java.util.Optional;

@Service
public class UsersService implements IUsersService {

    private UsersRepository _usersRepository;

    @Autowired
    UsersService(UsersRepository usersRepository) {
        this._usersRepository = usersRepository;
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
}