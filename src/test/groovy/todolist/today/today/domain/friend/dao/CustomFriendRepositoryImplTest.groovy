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
import todolist.today.today.global.config.QueryDslConfig
import todolist.today.today.global.dto.request.PagingRequest

@DataJpaTest
@Import([JpaAuditingConfig, QueryDslConfig])
class CustomFriendRepositoryImplTest extends Specification {

    @Autowired
    JPAQueryFactory jpaQueryFactory
    CustomFriendRepositoryImpl customFriendRepository

    @Autowired
    UserRepository userRepository

    @Autowired
    FriendRepository friendRepository

    def setup() {
        customFriendRepository = new CustomFriendRepositoryImpl(jpaQueryFactory)
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
        user = userRepository.save(user)

        User friendUser = User.builder()
                .email("tomorrow043149@gmail.com")
                .password("")
                .nickname("nickname")
                .profile("profile")
                .build()
        friendUser = userRepository.save(friendUser)

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
        user = userRepository.save(user)

        User friendUser = User.builder()
                .email("tomorrow043149@gmail.com")
                .password("")
                .nickname("nickname")
                .profile("profile")
                .build()
        friendUser = userRepository.save(friendUser)

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

    def "test existsFriend" () {
        given:
        User user = User.builder()
                .email("today043149@gmail.com")
                .password("")
                .nickname("")
                .profile("")
                .build()
        user = userRepository.save(user)

        User friendUser = User.builder()
                .email("tomorrow043149@gmail.com")
                .password("")
                .nickname("nickname")
                .profile("profile")
                .build()
        friendUser = userRepository.save(friendUser)

        Friend friend = Friend.builder()
                .friend(user)
                .user(friendUser)
                .build()
        friendRepository.save(friend)

        expect:
        customFriendRepository.existsFriend(user.getEmail(), friendUser.getEmail())
    }

}