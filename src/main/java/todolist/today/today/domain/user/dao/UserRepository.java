package todolist.today.today.domain.user.dao;

import org.springframework.data.repository.CrudRepository;
import todolist.today.today.domain.user.domain.User;

public interface UserRepository extends CrudRepository<User, String> {
}
