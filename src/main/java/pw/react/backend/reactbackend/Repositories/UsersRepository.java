package pw.react.backend.reactbackend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import  pw.react.backend.reactbackend.Models.User;

import java.util.List;

public interface UsersRepository extends JpaRepository<User, String> {
    List<User> findByLogin(String login);
    User findById(int id);

}
