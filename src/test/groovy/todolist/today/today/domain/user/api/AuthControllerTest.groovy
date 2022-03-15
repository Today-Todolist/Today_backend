package todolist.today.today.domain.user.api

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Specification
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.domain.User
import todolist.today.today.domain.user.dto.request.LoginRequest
import todolist.today.today.domain.user.dto.request.TokenRefreshRequest
import todolist.today.today.global.security.service.properties.JwtProperties

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static todolist.today.today.RequestUtil.makeLoginRequest
import static todolist.today.today.RequestUtil.makeTokenRefreshRequest

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest extends Specification {

    @Autowired
    MockMvc mvc

    @Autowired
    PasswordEncoder passwordEncoder

    @Autowired
    UserRepository userRepository

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    JwtProperties jwtProperties

    static final String EMAIL = "today043149@gmail.com"
    static final String PASSWORD = "password"

    def cleanup() {
        userRepository.deleteAll()
    }

    def "test login" () {
        given:
        User user = User.builder()
                .email(EMAIL)
                .password(passwordEncoder.encode(PASSWORD))
                .nickname("today")
                .profile("profile")
                .build()
        userRepository.save(user)

        LoginRequest request = makeLoginRequest(requestEmail, requestPassword)

        when:
        ResultActions result = mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())

        then:
        result.andExpect(status().is(status))

        where:
        requestEmail | requestPassword || status
        EMAIL | PASSWORD || 200
        "tomorrow043149@gmail.com" | "asdfasdf" || 401
        EMAIL | "asdfasdf" || 401
    }

    def "test tokenRefresh" () {
        given:
        User user = User.builder()
                .email(EMAIL)
                .password("password")
                .nickname("today")
                .profile("profile")
                .build()
        userRepository.save(user)

        String refreshToken =
                Jwts.builder()
                        .setExpiration(new Date(System.currentTimeMillis() + (10 * 1000L)))
                        .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret())
                        .setIssuedAt(new Date())
                        .setSubject(userId)
                        .claim("type", type)
                        .compact()

        TokenRefreshRequest request = makeTokenRefreshRequest(refreshToken)

        when:
        ResultActions result = mvc.perform(post("/token-refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())

        then:
        result.andExpect(status().is(status))

        where:
        type | userId || status
        "refresh" | EMAIL || 200
        "access" | EMAIL || 401
        "access" | "asdf" || 401
    }

}