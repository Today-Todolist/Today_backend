package todolist.today.today.domain.search.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Specification
import todolist.today.today.domain.search.dao.SearchWordRepository
import todolist.today.today.domain.search.domain.SearchWord
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
class SearchControllerTest extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private UserRepository userRepository

    @Autowired
    private TemplateRepository templateRepository

    @Autowired
    private SearchWordRepository searchWordRepository

    @Autowired
    private JwtTokenProvider jwtTokenProvider

    private static String USER_ID = "today043149@gmail.com"

    def cleanup() {
        searchWordRepository.deleteAll()
        userRepository.deleteAll()
    }

    def "test getSearchWord" () {
        given:
        SearchWord searchWord = new SearchWord("word")
        searchWordRepository.save(searchWord)

        when:
        ResultActions result = mvc.perform(get("/search-word")
                .param("word", "wor"))
                .andDo(print())

        then:
        result.andExpect(status().is(200))
    }

    def "test getSearchAmount" () {
        when:
        ResultActions result = mvc.perform(get("/search-amount")
                .param("word", "wor"))
                .andDo(print())

        then:
        result.andExpect(status().is(200))
    }

    def "test getNicknameResult" () {
        given:
        User user = User.builder()
                .email(USER_ID)
                .password("")
                .nickname("nickname")
                .profile("")
                .build()
        userRepository.save(user)

        String token = jwtTokenProvider.generateAccessToken(USER_ID)

        when:
        ResultActions result = mvc.perform(get("/search-result/nickname")
                .header("Authorization", "Bearer " + token)
                .param("word", "nick")
                .param("page", "0")
                .param("size", "1"))
                .andDo(print())

        then:
        result.andExpect(status().is(200))
    }

    def "test getEmailResult" () {
        given:
        User user = User.builder()
                .email(USER_ID)
                .password("")
                .nickname("")
                .profile("")
                .build()
        userRepository.save(user)

        String token = jwtTokenProvider.generateAccessToken(USER_ID)

        when:
        ResultActions result = mvc.perform(get("/search-result/email")
                .header("Authorization", "Bearer " + token)
                .param("word", "today")
                .param("page", "0")
                .param("size", "1"))
                .andDo(print())

        then:
        result.andExpect(status().is(200))
    }

    def "test getTemplateResult" () {
        given:
        User user = User.builder()
                .email("today043149@gmail.com")
                .password("")
                .nickname("")
                .profile("")
                .build()
        user = userRepository.save(user)

        Template template = Template.builder()
                .user(user)
                .size(7)
                .title("title")
                .profile("")
                .build()
        templateRepository.save(template)

        when:
        ResultActions result = mvc.perform(get("/search-result/template")
                .param("word", "titl")
                .param("page", "0")
                .param("size", "1"))
                .andDo(print())

        then:
        result.andExpect(status().is(200))
    }

}