package todolist.today.today.domain.user.domain.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@RedisHash(timeToLive = 300)
public class ChangePasswordCertify {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Indexed
    private String email;

    private String password;

    @Builder
    public ChangePasswordCertify(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
