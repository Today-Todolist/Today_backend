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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class FriendInfoControllerTest extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private UserRepository userRepository

    @Autowired
    private FriendRepository friendRepository

    @Autowired
    private FriendApplyRepository friendApplyRepository

    @Autowired
    private JwtTokenProvider jwtTokenProvider

    private static String USER_ID = "today043149@gmail.com"
    private static String FRIEND_ID = "tomorrow043149@gmail.com"

    def cleanup() {
        userRepository.deleteAll()
    }

    def "test getUserFriends" () {
        given:
        User user = User.builder()
                .email(USER_ID)
                .password("")
                .nickname("")
                .profile("")
                .build()
        user = userRepository.save(user)

        User friendUser = User.builder()
                .email(FRIEND_ID)
                .password("")
                .nickname("")
                .profile("")
                .build()
        friendUser = userRepository.save(friendUser)

        Friend friend = Friend.builder()
                .friend(user)
                .user(friendUser)
                .build()
        friendRepository.save(friend)

        String token = jwtTokenProvider.generateAccessToken(USER_ID)

        when:
        ResultActions result = mvc.perform(get("/{email}/friends", FRIEND_ID)
                .header("Authorization", "Bearer " + token)
                .param("page", "0")
                .param("size", "1"))
                .andDo(print())

        then:
        result.andExpect(status().is(200))
    }

    def "test getUserFriendsApply" () {
        given:
        User user = User.builder()
                .email(USER_ID)
                .password("")
                .nickname("")
                .profile("")
                .build()
        user = userRepository.save(user)

        User friend = User.builder()
                .email(FRIEND_ID)
                .password("")
                .nickname("")
                .profile("")
                .build()
        friend = userRepository.save(friend)

        FriendApply friendApply = FriendApply.builder()
                .friend(user)
                .user(friend)
                .build()
        friendApplyRepository.save(friendApply)

        String token = jwtTokenProvider.generateAccessToken(USER_ID)

        when:
        ResultActions result = mvc.perform(get("/friend-applys")
                .header("Authorization", "Bearer " + token)
                .param("page", "0")
                .param("size", "1"))
                .andDo(print())

        then:
        result.andExpect(status().is(200))
    }

}