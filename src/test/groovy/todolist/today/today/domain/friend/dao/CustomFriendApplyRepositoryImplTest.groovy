package todolist.today.today.domain.friend.dao

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import spock.lang.Specification
import todolist.today.today.domain.friend.domain.FriendApply
import todolist.today.today.domain.friend.dto.response.UserFriendApplyResponse
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.domain.User
import todolist.today.today.global.config.JpaAuditingConfig
import todolist.today.today.global.config.QueryDslConfig
import todolist.today.today.global.dto.request.PagingRequest

import javax.persistence.EntityManager

@DataJpaTest
@Import([JpaAuditingConfig, QueryDslConfig])
class CustomFriendApplyRepositoryImplTest extends Specification {

    @Autowired
    JPAQueryFactory jpaQueryFactory
    CustomFriendApplyRepositoryImpl customFriendApplyRepository

    @Autowired
    UserRepository userRepository

    @Autowired
    FriendApplyRepository friendApplyRepository

    def setup() {
        customFriendApplyRepository = new CustomFriendApplyRepositoryImpl(jpaQueryFactory)
    }

    def "test getUserFriendApply" () {
        given:
        PagingRequest request = new PagingRequest(0, 2)
        User user = User.builder()
                .email("today043149@gmail.com")
                .password("")
                .nickname("")
                .profile("")
                .build()
        user = userRepository.save(user)

        User friend = User.builder()
                .email("today043149@gmail.com")
                .password("")
                .nickname("nickname")
                .profile("profile")
                .build()
        friend = userRepository.save(friend)

        FriendApply friendApply = FriendApply.builder()
                .friend(user)
                .user(friend)
                .build()
        friendApplyRepository.save(friendApply)

        when:
        List<UserFriendApplyResponse> response = customFriendApplyRepository.getUserFriendApply(user.getEmail(), request)

        then:
        response.size() == 1
        response.get(0).getEmail() == friend.getEmail()
        response.get(0).getNickname() == friend.getNickname()
        response.get(0).getProfile() == friend.getProfile()
    }

    def "test existsFriendApply" () {
        given:
        User user = User.builder()
                .email("today043149@gmail.com")
                .password("")
                .nickname("")
                .profile("")
                .build()
        user = userRepository.save(user)

        User friend = User.builder()
                .email("tomorrow043149@gmail.com")
                .password("")
                .nickname("nickname")
                .profile("profile")
                .build()
        friend = userRepository.save(friend)

        FriendApply friendApply = FriendApply.builder()
                .friend(user)
                .user(friend)
                .build()
        friendApplyRepository.save(friendApply)

        expect:
        customFriendApplyRepository.existsFriendApply(user.getEmail(), friend.getEmail())
    }


}