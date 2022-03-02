package todolist.today.today.domain.user.dao

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import spock.lang.Specification
import todolist.today.today.domain.friend.dao.FriendRepository
import todolist.today.today.domain.friend.domain.Friend
import todolist.today.today.domain.template.dao.TemplateRepository
import todolist.today.today.domain.template.domain.Template
import todolist.today.today.domain.user.domain.User
import todolist.today.today.domain.user.dto.response.MyInfoResponse
import todolist.today.today.domain.user.dto.response.UserInfoResponse
import todolist.today.today.global.config.JpaAuditingConfig

import javax.persistence.EntityManager

@DataJpaTest
@Import(JpaAuditingConfig)
class CustomUserRepositoryImplTest extends Specification {

    @Autowired
    private EntityManager em
    private JPAQueryFactory jpaQueryFactory
    private CustomUserRepositoryImpl customUserRepository

    @Autowired
    private UserRepository userRepository

    @Autowired
    private FriendRepository friendRepository

    @Autowired
    private TemplateRepository templateRepository

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
        String nickname = customUserRepository.findNicknameById(user.getEmail())

        then:
        nickname == user.getNickname()
    }

    def "test findChangePossibleById" () {
        given:
        User user = User.builder()
                .email("today043149@gmail.com")
                .password("")
                .nickname("")
                .profile("")
                .build()
        userRepository.save(user)

        when:
        boolean changePossible = customUserRepository.findChangePossibleById(user.getEmail())

        then:
        changePossible
    }

    def "test getMyInfo" () {
        given:
        User user = User.builder()
                .email("today043149@gmail.com")
                .password("")
                .nickname("")
                .profile("")
                .build()
        user = userRepository.save(user)

        Friend friend = Friend.builder()
                .friend(user)
                .user(user)
                .build()
        friendRepository.save(friend)

        Template template = Template.builder()
                .user(user)
                .size(7)
                .title("")
                .profile("")
                .build()
        templateRepository.save(template)

        when:
        MyInfoResponse response = customUserRepository.getMyInfo(user.getEmail())

        then:
        response.getEmail() == user.getEmail()
        response.getNickname() == user.getNickname()
        response.getProfile() == user.getProfile()
        response.getFriendsAmount() == 1L
        response.getTemplates().size() == 1
        response.getTemplates().get(0).getId() == template.getTemplateId().toString()
        response.getTemplates().get(0).getTitle() == template.getTitle()
        response.getTemplates().get(0).getProfile() == template.getProfile()
    }

    def "test getUserInfo" () {
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
                .nickname("")
                .profile("")
                .build()
        friendUser = userRepository.save(friendUser)

        Friend friend = Friend.builder()
                .friend(friendUser)
                .user(user)
                .build()
        friendRepository.save(friend)

        Template template = Template.builder()
                .user(user)
                .size(7)
                .title("")
                .profile("")
                .build()
        templateRepository.save(template)

        when:
        UserInfoResponse response = customUserRepository.getUserInfo(user.getEmail(), friendUser.getEmail())

        then:
        response.getNickname() == user.getNickname()
        response.getProfile() == user.getProfile()
        response.getFriendsAmount() == 1L
        response.getStatus() == 1
        response.getTemplates().size() == 1
        response.getTemplates().get(0).getId() == template.getTemplateId().toString()
        response.getTemplates().get(0).getTitle() == template.getTitle()
        response.getTemplates().get(0).getProfile() == template.getProfile()
    }

}