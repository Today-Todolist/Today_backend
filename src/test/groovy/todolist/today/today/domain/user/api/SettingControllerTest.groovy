package todolist.today.today.domain.user.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.RequestPostProcessor
import spock.lang.Specification
import todolist.today.today.domain.todolist.dao.TodolistRepository
import todolist.today.today.domain.todolist.domain.Todolist
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.domain.User
import todolist.today.today.domain.user.dto.request.ChangeNicknameRequest
import todolist.today.today.domain.user.dto.request.ChangePasswordRequest
import todolist.today.today.domain.user.dto.request.DeleteUserRequest
import todolist.today.today.domain.user.dto.request.ResetTodolistRequest
import todolist.today.today.global.security.service.JwtTokenProvider
import todolist.today.today.infra.file.image.ImageUploadFacade

import java.time.LocalDate

import static org.mockito.ArgumentMatchers.any
import static org.mockito.BDDMockito.given
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static todolist.today.today.RequestUtil.*

@SpringBootTest
@AutoConfigureMockMvc
class SettingControllerTest extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private UserRepository userRepository

    @Autowired
    private TodolistRepository todolistRepository

    @Autowired
    private JwtTokenProvider jwtTokenProvider

    @Autowired
    private ObjectMapper objectMapper

    @Autowired
    private PasswordEncoder passwordEncoder

    @MockBean
    private ImageUploadFacade imageUploadFacade

    private static final String EMAIL = "today043149@gmail.com"
    private static final String PASSWORD = "password"
    private static final String NICKNAME = "today"

    def cleanup() {
        userRepository.deleteAll()
    }

    def "test changeProfile" () {
        given:
        User user = User.builder()
                .email(EMAIL)
                .password("")
                .nickname("")
                .profile("")
                .build()
        userRepository.save(user)

        MockMultipartFile image = new MockMultipartFile("file", "image.png", "image/png", "".getBytes())
        given(imageUploadFacade.uploadImage(any())).willReturn("profile")

        String token = jwtTokenProvider.generateAccessToken(EMAIL)

        when:
        ResultActions result = mvc.perform(multipart("/profile")
                .file(image)
                .with(new RequestPostProcessor() {
                    @Override
                    MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                        request.setMethod("PUT")
                        return request
                    }
                })
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header("Authorization", "Bearer " + token))
                .andDo(print())

        then:
        result.andExpect(status().is(201))
    }

    def "test changeNickname" () {
        given:
        User user = User.builder()
                .email(EMAIL)
                .password("")
                .nickname(NICKNAME)
                .profile("")
                .build()
        userRepository.save(user)

        ChangeNicknameRequest request = makeChangeNicknameRequest(requestNickname)
        String token = jwtTokenProvider.generateAccessToken(EMAIL)

        when:
        ResultActions result = mvc.perform(put("/nickname")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())

        then:
        result.andExpect(status().is(status))

        where:
        requestNickname || status
        "tomorrow" || 201
        NICKNAME || 409
    }

    def "test changePassword" () {
        given:
        User user = User.builder()
                .email(EMAIL)
                .password(passwordEncoder.encode(PASSWORD))
                .nickname("")
                .profile("")
                .build()
        userRepository.save(user)

        ChangePasswordRequest request = makeChangePasswordRequest(requestPassword, "newPassword")
        String token = jwtTokenProvider.generateAccessToken(EMAIL)

        when:
        ResultActions result = mvc.perform(put("/password")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())

        then:
        result.andExpect(status().is(status))

        where:
        requestPassword || status
        PASSWORD || 201
        "wrongPassword" || 401
    }

    def "test changeEditAvailability" () {
        given:
        User user = User.builder()
                .email(EMAIL)
                .password("")
                .nickname("")
                .profile("")
                .build()
        userRepository.save(user)
        String token = jwtTokenProvider.generateAccessToken(EMAIL)

        when:
        ResultActions result = mvc.perform(post("/edit-availability/{changePossible}", requestChangePossible)
                .header("Authorization", "Bearer " + token))
                .andDo(print())

        then:
        result.andExpect(status().is(status))

        where:
        requestChangePossible || status
        "on" || 201
        "off" || 201
    }

    def "test resetTodolist" () {
        given:
        User user = User.builder()
                .email(EMAIL)
                .password(passwordEncoder.encode(PASSWORD))
                .nickname("")
                .profile("")
                .build()
        user = userRepository.save(user)

        Todolist todolist = Todolist.builder()
                .user(user)
                .date(LocalDate.now())
                .build()
        todolistRepository.save(todolist)

        ResetTodolistRequest request = makeResetTodolistRequest(requestPassword)
        String token = jwtTokenProvider.generateAccessToken(EMAIL)

        when:
        ResultActions result = mvc.perform(post("/reset-todolist")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())

        then:
        result.andExpect(status().is(status))

        where:
        requestPassword || status
        PASSWORD || 204
        "wrongPassword" || 401
    }

    def "test deleteUser" () {
        given:
        User user = User.builder()
                .email(EMAIL)
                .password(passwordEncoder.encode(PASSWORD))
                .nickname("")
                .profile("")
                .build()
        userRepository.save(user)

        DeleteUserRequest request = makeDeleteUserRequest(requestPassword)
        String token = jwtTokenProvider.generateAccessToken(EMAIL)

        when:
        ResultActions result = mvc.perform(post("/delete-user")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())

        then:
        result.andExpect(status().is(status))

        where:
        requestPassword || status
        PASSWORD || 204
        "wrongPassword" || 401
    }

}