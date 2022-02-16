package todolist.today.today.domain.user.dao.redis;

import org.springframework.data.repository.CrudRepository;
import todolist.today.today.domain.user.domain.redis.SignUpCertify;

public interface SignUpCertifyRepository extends CrudRepository<SignUpCertify, Long> {

    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

}
