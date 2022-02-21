package todolist.today.today.domain.user.dao.redis;

import org.springframework.data.repository.CrudRepository;
import todolist.today.today.domain.user.domain.redis.SignUpCertify;

public interface SignUpCertifyRepository extends CrudRepository<SignUpCertify, Long> {

    SignUpCertify findByEmail(String email);
    SignUpCertify findByNickname(String nickname);

    default boolean existsByEmail(String email) {
        SignUpCertify certify = findByEmail(email);
        return certify != null;
    }

    default boolean existsByNickname(String nickname) {
        SignUpCertify certify = findByNickname(nickname);
        return certify != null;
    }

}
