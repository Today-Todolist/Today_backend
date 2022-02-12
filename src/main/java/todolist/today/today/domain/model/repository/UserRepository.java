package todolist.today.today.domain.model.repository;

import org.springframework.data.repository.CrudRepository;
import todolist.today.today.domain.model.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findById(String email);

}
