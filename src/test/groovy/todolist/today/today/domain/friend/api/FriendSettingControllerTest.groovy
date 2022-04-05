package todolist.today.today.domain.friend.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Specification
import todolist.today.today.domain.friend.dao.FriendApplyRepository
import todolist.today.today.domain.friend.dao.FriendRepository
import todolist.today.today.domain.friend.domain.Friend
import todolist.today.today.domain.friend.domain.FriendApply
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.domain.User
import todolist.today.today.global.security.service.JwtTokenProvider

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class FriendSettingControllerTest extends Specification {

    @Autowired
    MockMvc mvc

    @Autowired
    UserRepository userRepository

    @Autowired
    FriendRepository friendRepository

    @Autowired
    FriendApplyRepository friendApplyRepository

    @Autowired
    JwtTokenProvider jwtTokenProvider

    static final String USER_ID = "today043149@gmail.com"
    static final String FRIEND_ID = "tomorrow043149@gmail.com"

    def cleanup() {
        userRepository.deleteAll()
    }

    def "test makeFriendApply" () {
        given:
        User user = User.builder()
                .email(USER_ID)
                .password("")
                .nickname("")
                .profile("")
                .build()
        userRepository.save(user)

        User friend = User.builder()
                .email(FRIEND_ID)
                .password("")
                .nickname("nickname")
                .profile("profile")
                .build()
        userRepository.save(friend)

        String token = jwtTokenProvider.generateAccessToken(USER_ID)

        when:
        ResultActions result = mvc.perform(post("/friend-apply/{email}", requestEmail)
                .header("Authorization", "Bearer " + token))
                .andDo(print())

        then:
        result.andExpect(status().is(status))

        where:
        requestEmail || status
        FRIEND_ID || 201
        "emm@gmail.com" || 404
    }

    def "test deleteFriend" () {
        given:
        User user = User.builder()
                .email(USER_ID)
                .password("")
                .nickname("")
                .profile("")
                .build()
        userRepository.save(user)

        User friendUser = User.builder()
                .email(FRIEND_ID)
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

        String token = jwtTokenProvider.generateAccessToken(USER_ID)

        when:
        ResultActions result = mvc.perform(delete("/friend/{email}", FRIEND_ID)
                .header("Authorization", "Bearer " + token))
                .andDo(print())

        then:
        result.andExpect(status().is(204))
    }

    def "test makeFriend" () {
        given:
        User user = User.builder()
                .email(USER_ID)
                .password("")
                .nickname("")
                .profile("")
                .build()
        userRepository.save(user)

        User friend = User.builder()
                .email(FRIEND_ID)
                .password("")
                .nickname("nickname")
                .profile("profile")
                .build()
        userRepository.save(friend)

        FriendApply friendApply = new FriendApply(friend, user)
        friendApplyRepository.save(friendApply)

        String token = jwtTokenProvider.generateAccessToken(USER_ID)

        when:
        ResultActions result = mvc.perform(post("/friend/{email}", requestEmail)
                .header("Authorization", "Bearer " + token))
                .andDo(print())

        then:
        result.andExpect(status().is(status))

        where:
        requestEmail || status
        FRIEND_ID || 201
        "emm@gmail.com" || 404
    }

    def "test deleteFriendApply" () {
        given:
        User user = User.builder()
                .email(USER_ID)
                .password("")
                .nickname("")
                .profile("")
                .build()
        userRepository.save(user)

        User friend = User.builder()
                .email(FRIEND_ID)
                .password("")
                .nickname("nickname")
                .profile("profile")
                .build()
        userRepository.save(friend)

        FriendApply friendApply = FriendApply.builder()
                .friend(user)
                .user(friend)
                .build()
        friendApplyRepository.save(friendApply)

        String token = jwtTokenProvider.generateAccessToken(USER_ID)

        when:
        ResultActions result = mvc.perform(delete("/friend-apply/{email}", FRIEND_ID)
                .header("Authorization", "Bearer " + token))
                .andDo(print())

        then:
        result.andExpect(status().is(204))
    }

}