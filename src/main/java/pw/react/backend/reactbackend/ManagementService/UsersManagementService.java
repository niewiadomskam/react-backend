package pw.react.backend.reactbackend.ManagementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.react.backend.reactbackend.Models.User;
import pw.react.backend.reactbackend.Repositories.UsersRepository;

import java.util.List;

@Service
public class UsersManagementService {
    private UsersRepository _usersRepository;

    @Autowired
    public UsersManagementService(UsersRepository usersRepository) {
        _usersRepository = usersRepository;
    }

    public List<User> findAll() {
        return _usersRepository.findAll();
    }

    public List<User> findByLogin(String login) {
        return _usersRepository.FindByLogin(login);
    }

    public User save(User user) {
        return _usersRepository.save(user);
    }

    public boolean exists(User user) {
        List<User> result = _usersRepository.FindByLogin(user.GetLogin());
        return result != null && !result.isEmpty();
    }
}