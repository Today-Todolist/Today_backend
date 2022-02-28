package todolist.today.today.domain.user.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Specification
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.dao.redis.ChangePasswordCertifyRepository
import todolist.today.today.domain.user.dao.redis.SignUpCertifyRepository
import todolist.today.today.domain.user.domain.User
import todolist.today.today.domain.user.domain.redis.ChangePasswordCertify
import todolist.today.today.domain.user.domain.redis.SignUpCertify
import todolist.today.today.infra.file.image.ImageUploadFacade

import static org.mockito.BDDMockito.given
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static todolist.today.today.RequestUtil.inputField

@SpringBootTest
@AutoConfigureMockMvc
class CertifyReceiveControllerTest extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private SignUpCertifyRepository signUpCertifyRepository

    @Autowired
    private ChangePasswordCertifyRepository changePasswordCertifyRepository

    @Autowired
    private UserRepository userRepository

    @MockBean
    private ImageUploadFacade imageUploadFacade

    private static final String EMAIL = "today403149@gmail.com"
    private static final long TOKEN = 1234L

    def cleanup() {
        signUpCertifyRepository.deleteAll()
        userRepository.deleteAll()
    }

    def "test receiveSignUpCertify" () {
        given:
        SignUpCertify certify = SignUpCertify.builder()
                .email(EMAIL)
                .password("password")
                .nickname("today")
                .build()
        inputField(certify, "id", TOKEN)
        signUpCertifyRepository.save(certify)
        given(imageUploadFacade.uploadRandomImage()).willReturn("profile")

        when:
        ResultActions result = mvc.perform(post("/sign-up")
                .param("email", email)
                .param("token", token as String))
                .andDo(print())

        then:
        result.andExpect(status().is(status))

        where:
        email | token || status
        EMAIL | TOKEN || 201
        "tomorrow043149@gmail.com" | TOKEN || 401
        EMAIL | 1111L || 401
    }

    def "test receiveChangePasswordCertify" () {
        given:
        User user = User.builder()
                .email(EMAIL)
                .password("password")
                .nickname("today")
                .profile("profile")
                .build()
        userRepository.save(user)

        ChangePasswordCertify certify = ChangePasswordCertify.builder()
                .email(certifyEmail)
                .password("new_password")
                .build()
        inputField(certify, "id", TOKEN)
        changePasswordCertifyRepository.save(certify)

        when:
        ResultActions result = mvc.perform(post("/reset-password")
                .param("email", requestEmail)
                .param("token", token as String))
                .andDo(print())

        then:
        result.andExpect(status().is(status))

        where:
        certifyEmail | requestEmail | token || status
        EMAIL | EMAIL | TOKEN || 201
        EMAIL | EMAIL | 1111L || 401
        EMAIL | "tomorrow043149@gmail.com" | TOKEN || 401
        "tomorrow043149@gmail.com" | "tomorrow043149@gmail.com" | TOKEN || 404
    }

}