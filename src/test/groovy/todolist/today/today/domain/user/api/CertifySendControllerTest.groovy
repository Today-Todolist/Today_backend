package todolist.today.today.domain.user.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Specification
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.dao.redis.ChangePasswordCertifyRepository
import todolist.today.today.domain.user.dao.redis.SignUpCertifyRepository
import todolist.today.today.domain.user.domain.User
import todolist.today.today.domain.user.domain.redis.SignUpCertify
import todolist.today.today.domain.user.dto.request.ChangePasswordCertifySendRequest
import todolist.today.today.domain.user.dto.request.SignUpCertifySendRequest
import todolist.today.today.infra.mail.MailSendFacade

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static todolist.today.today.RequestUtil.makeChangePasswordCertifySendRequest
import static todolist.today.today.RequestUtil.makeSignUpCertifySendRequest

@SpringBootTest
@AutoConfigureMockMvc
class CertifySendControllerTest extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private SignUpCertifyRepository signUpCertifyRepository

    @Autowired
    private ChangePasswordCertifyRepository changePasswordCertifyRepository

    @Autowired
    private UserRepository userRepository

    @Autowired
    private ObjectMapper objectMapper

    @MockBean
    private MailSendFacade mailSendFacade

    private static final String EMAIL = "today403149@gmail.com"
    private static final String NICKNAME = "today"

    def cleanup() {
        signUpCertifyRepository.deleteAll()
        changePasswordCertifyRepository.deleteAll()
    }

    def "test sendSignUpCertify" () {
        given:
        SignUpCertify certify = SignUpCertify.builder()
                .email(certifyEmail)
                .password("password")
                .nickname(certifyNickname)
                .build()
        signUpCertifyRepository.save(certify)

        User user = User.builder()
                .email(userEmail)
                .password("password")
                .nickname(userNickname)
                .profile("profile")
                .build()
        userRepository.save(user)

        SignUpCertifySendRequest request = makeSignUpCertifySendRequest(requestEmail, "password", requestNickname)

        when:
        ResultActions result = mvc.perform(post("/sign-up/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())

        then:
        result.andExpect(status().is(status))

        where:
        certifyEmail | userEmail | certifyNickname | userNickname | requestEmail | requestNickname || status
        EMAIL | EMAIL | NICKNAME | NICKNAME | "tomorrow043149@gmail.com" | "tomorrow" || 200
        "tomorrow043149@gmail.com" | EMAIL | NICKNAME | NICKNAME | EMAIL | NICKNAME || 409
        EMAIL | "tomorrow043149@gmail.com" | NICKNAME | NICKNAME | EMAIL | NICKNAME || 409
        EMAIL | EMAIL | "tomorrow" | NICKNAME | EMAIL | NICKNAME || 409
        EMAIL | EMAIL | NICKNAME | "tomorrow" | EMAIL | NICKNAME || 409
    }

    def "test sendChangePasswordCertify" () {
        given:
        User user = User.builder()
                .email(userEmail)
                .password("password")
                .nickname("today")
                .profile("profile")
                .build()
        userRepository.save(user)

        ChangePasswordCertifySendRequest request = makeChangePasswordCertifySendRequest(requestEmail, "newPassword")

        when:
        ResultActions result = mvc.perform(post("/password-recovery/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())

        then:
        result.andExpect(status().is(status))

        where:
        userEmail | requestEmail || status
        EMAIL | EMAIL || 200
        EMAIL | "tomorrow043149@gmail.com" || 404
    }

}