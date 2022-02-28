package todolist.today.today.domain.user.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Specification
import todolist.today.today.domain.template.dao.TemplateRepository
import todolist.today.today.domain.template.domain.Template
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.domain.User
import todolist.today.today.domain.user.dto.request.CheckPasswordRequest
import todolist.today.today.global.security.service.JwtTokenProvider

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static todolist.today.today.RequestUtil.makeCheckPasswordRequest

@SpringBootTest
@AutoConfigureMockMvc
class CheckControllerTest extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private UserRepository userRepository

    @Autowired
    private TemplateRepository templateRepository

    @Autowired
    private JwtTokenProvider jwtTokenProvider

    @Autowired
    private PasswordEncoder passwordEncoder

    @Autowired
    private ObjectMapper objectMapper

    private static final String EMAIL = "today043149@gmail.com"
    private static final String NICKNAME = "today"
    private static final String PASSWORD = "password"
    private static final String TEMPLATE_TITLE = "title"

    def cleanup() {
        userRepository.deleteAll()
        templateRepository.deleteAll()
    }

    def "test checkEmail" () {
        given:
        User user = User.builder()
                .email(EMAIL)
                .password("password")
                .nickname("today")
                .profile("profile")
                .build()
        userRepository.save(user)

        when:
        ResultActions result = mvc.perform(get("/{email}/email-availability", requestEmail)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())

        then:
        result.andExpect(status().is(status))

        where:
        requestEmail || status
        "tomorrow043149@gmail.com" || 200
        EMAIL || 409
    }

    def "test checkNickname" () {
        given:
        User user = User.builder()
                .email("today043149@gmail.com")
                .password("password")
                .nickname(NICKNAME)
                .profile("profile")
                .build()
        userRepository.save(user)

        when:
        ResultActions result = mvc.perform(get("/{email}/nickname-availability", requestNickname)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())

        then:
        result.andExpect(status().is(status))

        where:
        requestNickname || status
        "tomorrow" || 200
        NICKNAME || 409
    }

    def "test checkPassword" () {
        given:
        User user = User.builder()
                .email(EMAIL)
                .password(passwordEncoder.encode(PASSWORD))
                .nickname("today")
                .profile("profile")
                .build()
        userRepository.save(user)

        String token = jwtTokenProvider.generateAccessToken(EMAIL)
        CheckPasswordRequest request = makeCheckPasswordRequest(requestPassword)

        when:
        ResultActions result = mvc.perform(post("/validation-password")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())

        then:
        result.andExpect(status().is(status))

        where:
        requestPassword || status
        PASSWORD || 200
        "wrongPassword" || 401
    }

    def "test checkTemplateTitle" () {
        given:
        User user = User.builder()
                .email(EMAIL)
                .password("password")
                .nickname("today")
                .profile("profile")
                .build()
        userRepository.save(user)

        Template template = Template.builder()
                .user(user)
                .size(7)
                .title(TEMPLATE_TITLE)
                .profile("profile")
                .build()
        templateRepository.save(template)

        String token = jwtTokenProvider.generateAccessToken(EMAIL)

        when:
        ResultActions result = mvc.perform(get("/{title}/template-availability", requestTitle)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andDo(print())

        then:
        result.andExpect(status().is(status))

        where:
        requestTitle || status
        "wrongTitle" || 200
        TEMPLATE_TITLE || 409
    }

    def "test checkEditAvailability" () {
        given:
        User user = User.builder()
                .email(EMAIL)
                .password("password")
                .nickname("today")
                .profile("profile")
                .build()
        user.changeChangePossible(changePossible)
        userRepository.save(user)

        String token = jwtTokenProvider.generateAccessToken(EMAIL)

        when:
        ResultActions result = mvc.perform(get("/edit-availability")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andDo(print())

        then:
        result.andExpect(status().is(status))

        where:
        changePossible || status
        true || 200
        false || 409
    }

}