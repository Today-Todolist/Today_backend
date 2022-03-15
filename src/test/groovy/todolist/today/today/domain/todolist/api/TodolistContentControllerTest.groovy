package todolist.today.today.domain.todolist.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Specification
import todolist.today.today.domain.todolist.dao.TodolistContentRepository
import todolist.today.today.domain.todolist.dao.TodolistRepository
import todolist.today.today.domain.todolist.dao.TodolistSubjectRepository
import todolist.today.today.domain.todolist.domain.Todolist
import todolist.today.today.domain.todolist.domain.TodolistContent
import todolist.today.today.domain.todolist.domain.TodolistSubject
import todolist.today.today.domain.todolist.dto.request.TodolistContentChangeRequest
import todolist.today.today.domain.todolist.dto.request.TodolistContentCreateRequest
import todolist.today.today.domain.todolist.dto.request.TodolistContentOrderRequest
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.domain.User
import todolist.today.today.global.security.service.JwtTokenProvider

import java.time.LocalDate

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static todolist.today.today.RequestUtil.*

@SpringBootTest
@AutoConfigureMockMvc
class TodolistContentControllerTest extends Specification {

    @Autowired
    private MockMvc mvc

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

    @Autowired
    private ObjectMapper objectMapper

    private User user
    private Todolist todolist
    private TodolistSubject subject

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
    }

    def cleanup() {
        userRepository.deleteAll()
    }

    def "test makeTodolistContent" () {
        given:
        String token = jwtTokenProvider.generateAccessToken(user.getEmail())
        TodolistContentCreateRequest request =
                makeTodolistContentCreateRequest(subject.getTodolistSubjectId().toString(), "content")

        when:
        ResultActions result = mvc.perform(post("/todolist/content")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())

        then:
        result.andExpect(status().is(201))
    }

    def "test changeTodolistContent" () {
        given:
        TodolistContent content = TodolistContent.builder()
                .todolistSubject(subject)
                .content("content")
                .value(100)
                .build()
        content = todolistContentRepository.save(content)

        String token = jwtTokenProvider.generateAccessToken(user.getEmail())
        TodolistContentChangeRequest request = makeTodolistContentChangeRequest("content")

        when:
        ResultActions result = mvc.perform(put("/todolist/content/{contentId}", content.getTodolistContentId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())

        then:
        result.andExpect(status().is(201))
    }

    def "test changeTodolistContentOrder" () {
        given:
        TodolistContent content = TodolistContent.builder()
                .todolistSubject(subject)
                .content("content")
                .value(100)
                .build()
        content = todolistContentRepository.save(content)

        String token = jwtTokenProvider.generateAccessToken(user.getEmail())
        TodolistContentOrderRequest request = makeTodolistContentOrderRequest(order)

        when:
        ResultActions result = mvc.perform(put("/todolist/content-order/{contentId}", content.getTodolistContentId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())

        then:
        result.andExpect(status().is(status))

        where:
        order || status
        0 || 201
        1 || 409
    }

    def "test deleteTodolistContent" () {
        given:
        TodolistContent content = TodolistContent.builder()
                .todolistSubject(subject)
                .content("content")
                .value(100)
                .build()
        content = todolistContentRepository.save(content)

        String token = jwtTokenProvider.generateAccessToken(user.getEmail())

        when:
        ResultActions result = mvc.perform(delete("/todolist/content/{contentId}", content.getTodolistContentId())
                .header("Authorization", "Bearer " + token))
                .andDo(print())

        then:
        result.andExpect(status().is(204))
    }

}