package todolist.today.today.domain.todolist.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Specification
import todolist.today.today.domain.todolist.dao.TodolistContentRepository
import todolist.today.today.domain.todolist.dao.TodolistRepository
import todolist.today.today.domain.todolist.dao.TodolistSubjectRepository
import todolist.today.today.domain.todolist.domain.Todolist
import todolist.today.today.domain.todolist.domain.TodolistContent
import todolist.today.today.domain.todolist.domain.TodolistSubject
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.domain.User
import todolist.today.today.global.security.service.JwtTokenProvider

import java.time.LocalDate

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static todolist.today.today.global.dto.LocalDateUtil.convert

@SpringBootTest
@AutoConfigureMockMvc
class TodolistInfoControllerTest extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private PasswordEncoder passwordEncoder

    @Autowired
    private UserRepository userRepository

    @Autowired
    private TodolistRepository todolistRepository

    @Autowired
    private TodolistSubjectRepository todolistSubjectRepository

    @Autowired
    private TodolistContentRepository todolistContentRepository

    @Autowired
    private JwtTokenProvider jwtTokenProvider

    private User user
    private Todolist todolist
    private TodolistSubject subject
    private TodolistContent content

    def setup() {
        user = User.builder()
                .email("today043149@gmail.com")
                .password("")
                .nickname("")
                .profile("")
                .build()
        user = userRepository.save(user)

        todolist = Todolist.builder()
                .user(user)
                .date(LocalDate.now())
                .build()
        todolist = todolistRepository.save(todolist)

        subject = TodolistSubject.builder()
                .todolist(todolist)
                .subject("subject")
                .value(100)
                .build()
        subject = todolistSubjectRepository.save(subject)

        content = TodolistContent.builder()
                .todolistSubject(subject)
                .content("content")
                .value(100)
                .build()
        content = todolistContentRepository.save(content)
    }

    def "test getTodolistRecord" () {
        when:
        ResultActions result = mvc.perform(get("/{email}/record", user.getEmail())
                .param("from", convert(LocalDate.now().minusDays(10)))
                .param("to", convert(LocalDate.now().plusDays(10))))
                .andDo(print())

        then:
        result.andExpect(status().is(200))
    }

    def "test getMyCalendar" () {
        given:
        String token = jwtTokenProvider.generateAccessToken(user.getEmail())
        String date = convert(LocalDate.now()).substring(0, 7)

        when:
        ResultActions result = mvc.perform(get("/calendar")
                .header("Authorization", "Bearer " + token)
                .param("date", date))
                .andDo(print())

        then:
        result.andExpect(status().is(200))
    }

    def "test getUserCalendar" () {
        given:
        String date = convert(LocalDate.now()).substring(0, 7)

        when:
        ResultActions result = mvc.perform(get("/{email}/calendar", user.getEmail())
                .param("date", date))
                .andDo(print())

        then:
        result.andExpect(status().is(200))
    }

    def "test getTodolist" () {
        given:
        String token = jwtTokenProvider.generateAccessToken(user.getEmail())

        when:
        ResultActions result = mvc.perform(get("/todolist")
                .header("Authorization", "Bearer " + token)
                .param("date", convert(LocalDate.now())))
                .andDo(print())

        then:
        result.andExpect(status().is(200))
    }

}