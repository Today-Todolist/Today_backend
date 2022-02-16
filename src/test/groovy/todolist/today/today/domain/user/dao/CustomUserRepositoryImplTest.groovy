package todolist.today.today.domain.user.dao

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification
import todolist.today.today.domain.user.domain.User

import javax.persistence.EntityManager

@DataJpaTest
class CustomUserRepositoryImplTest extends Specification {

    @Autowired
    private EntityManager em
    private JPAQueryFactory jpaQueryFactory
    private CustomUserRepositoryImpl customUserRepository

    @Autowired
    private UserRepository userRepository

    def setup() {
        jpaQueryFactory = new JPAQueryFactory(em)
        customUserRepository = new CustomUserRepositoryImpl(new JPAQueryFactory(em))
    }

    def "test findPasswordById" () {
        given:
        User user = User.builder()
                .email("today043149@gmail.com")
                .password("password")
                .nickname("")
                .profile("")
                .build()
        userRepository.save(user)

        when:
        String password = customUserRepository.findPasswordById(user.getEmail())

        then:
        password == user.getPassword()
    }

    def "test findNicknameById" () {
        given:
        User user = User.builder()
                .email("today043149@gmail.com")
                .password("")
                .nickname("nickname")
                .profile("")
                .build()
        userRepository.save(user)

        when:
        String password = customUserRepository.findNicknameById(user.getEmail())

        then:
        password == user.getNickname()
    }

}