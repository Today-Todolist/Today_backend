package todolist.today.today.global.repository;

import org.springframework.data.repository.CrudRepository;
import todolist.today.today.global.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findById(String email);

}
