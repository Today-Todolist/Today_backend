package todolist.today.today.domain.user.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Specification
import todolist.today.today.domain.friend.dao.FriendRepository
import todolist.today.today.domain.friend.domain.Friend
import todolist.today.today.domain.template.dao.TemplateRepository
import todolist.today.today.domain.template.domain.Template
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.domain.User
import todolist.today.today.global.security.service.JwtTokenProvider

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class UserInfoControllerTest extends Specification {

    @Autowired
    MockMvc mvc

    @Autowired
    UserRepository userRepository

    @Autowired
    FriendRepository friendRepository

    @Autowired
    TemplateRepository templateRepository

    @Autowired
    JwtTokenProvider jwtTokenProvider

    static final String EMAIL = "today043149@gmail.com"

    def cleanup() {
        userRepository.deleteAll()
    }

    def "test getMyInfo" () {
        given:
        User user = User.builder()
                .email(EMAIL)
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

        String token = jwtTokenProvider.generateAccessToken(EMAIL)

        when:
        ResultActions result = mvc.perform(get("/info")
                .header("Authorization", "Bearer " + token))
                .andDo(print())

        then:
        result.andExpect(status().is(200))
    }

    def "test getUserInfo" () {
        given:
        User user = User.builder()
                .email(EMAIL)
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

        String token = jwtTokenProvider.generateAccessToken(EMAIL)

        when:
        ResultActions result = mvc.perform(get("/{email}/info", requestEmail)
                .header("Authorization", "Bearer " + token))
                .andDo(print())

        then:
        result.andExpect(status().is(status))

        where:
        requestEmail || status
        EMAIL || 200
        "tomorrow043149@gmail.com" || 404
    }

}