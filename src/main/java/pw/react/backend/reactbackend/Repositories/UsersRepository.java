package pw.react.backend.reactbackend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pw.react.backend.reactbackend.Models.User;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    List<User> FindByLogin(String login);
}