package todolist.today.today.domain.user.dao.redis;

import org.springframework.data.repository.CrudRepository;
import todolist.today.today.domain.user.domain.redis.ChangePasswordCertify;

public interface ChangePasswordCertifyRepository extends CrudRepository<ChangePasswordCertify, Long> {
}
