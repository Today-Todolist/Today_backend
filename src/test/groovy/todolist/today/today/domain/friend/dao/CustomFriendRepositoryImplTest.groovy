package todolist.today.today.domain.friend.dao

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import spock.lang.Specification
import todolist.today.today.domain.friend.domain.Friend
import todolist.today.today.domain.friend.domain.id.FriendRelation
import todolist.today.today.domain.friend.dto.response.UserFriendResponse
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.domain.User
import todolist.today.today.global.config.JpaAuditingConfig
import todolist.today.today.global.dto.request.PagingRequest

import javax.persistence.EntityManager

@DataJpaTest
@Import(JpaAuditingConfig)
class CustomFriendRepositoryImplTest extends Specification {

    @Autowired
    private EntityManager em
    private JPAQueryFactory jpaQueryFactory
    private CustomFriendRepositoryImpl customFriendRepository

    @Autowired
    private UserRepository userRepository

    @Autowired
    private FriendRepository friendRepository

    def setup() {
        jpaQueryFactory = new JPAQueryFactory(em)
        customFriendRepository = new CustomFriendRepositoryImpl(new JPAQueryFactory(em))
    }

    def "test getUserFriends" () {
        given:
        PagingRequest request = new PagingRequest(0, 2)
        User user = User.builder()
                .email("today043149@gmail.com")
                .password("")
                .nickname("")
                .profile("")
                .build()
        userRepository.save(user)

        User friendUser = User.builder()
                .email("tomorrow043149@gmail.com")
                .password("")
                .nickname("nickname")
                .profile("profile")
                .build()
        userRepository.save(friendUser)

        Friend friend = Friend.builder()
                .friend(user)
                .user(friendUser)
                .build()
        friendRepository.save(friend)

        when:
        List<UserFriendResponse> response = customFriendRepository.getUserFriends(friendUser.getEmail(), user.getEmail(), request)

        then:
        response.size() == 1
    }

    def "test deleteFriend" () {
        given:
        User user = User.builder()
                .email("today043149@gmail.com")
                .password("")
                .nickname("")
                .profile("")
                .build()
        userRepository.save(user)

        User friendUser = User.builder()
                .email("tomorrow043149@gmail.com")
                .password("")
                .nickname("nickname")
                .profile("profile")
                .build()
        userRepository.save(friendUser)

        Friend friend = Friend.builder()
                .friend(user)
                .user(friendUser)
                .build()
        friendRepository.save(friend)

        when:
        customFriendRepository.deleteFriend(user.getEmail(), friendUser.getEmail())

        then:
        !friendRepository.existsById(new FriendRelation(friendUser.getEmail(), user.getEmail()))
    }

}