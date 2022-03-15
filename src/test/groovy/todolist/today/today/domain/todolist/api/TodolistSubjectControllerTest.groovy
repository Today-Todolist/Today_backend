package todolist.today.today.domain.todolist.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Specification
import todolist.today.today.domain.todolist.dao.TodolistRepository
import todolist.today.today.domain.todolist.dao.TodolistSubjectRepository
import todolist.today.today.domain.todolist.domain.Todolist
import todolist.today.today.domain.todolist.domain.TodolistSubject
import todolist.today.today.domain.todolist.dto.request.TodolistSubjectChangeRequest
import todolist.today.today.domain.todolist.dto.request.TodolistSubjectCreateRequest
import todolist.today.today.domain.todolist.dto.request.TodolistSubjectOrderRequest
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.domain.User
import todolist.today.today.global.security.service.JwtTokenProvider

import java.time.LocalDate

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static todolist.today.today.RequestUtil.*
import static todolist.today.today.global.dto.LocalDateUtil.convert

@SpringBootTest
@AutoConfigureMockMvc
class TodolistSubjectControllerTest extends Specification {

    @Autowired
    MockMvc mvc

    @Autowired
    UserRepository userRepository

    @Autowired
    TodolistRepository todolistRepository

    @Autowired
    TodolistSubjectRepository todolistSubjectRepository

    @Autowired
    JwtTokenProvider jwtTokenProvider

    @Autowired
    ObjectMapper objectMapper

    User user
    Todolist todolist

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
    }

    def cleanup() {
        userRepository.deleteAll()
    }

    def "test makeTodolistSubject" () {
        given:
        String token = jwtTokenProvider.generateAccessToken(user.getEmail())
        TodolistSubjectCreateRequest request =
                makeTodolistSubjectCreateRequest(convert(LocalDate.now()), "subject")

        when:
        ResultActions result = mvc.perform(post("/todolist/subject")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())

        then:
        result.andExpect(status().is(201))
    }

    def "test changeTodolistSubject" () {
        given:
        TodolistSubject subject = TodolistSubject.builder()
                .todolist(todolist)
                .subject("subject")
                .value(100)
                .build()
        subject = todolistSubjectRepository.save(subject)

        String token = jwtTokenProvider.generateAccessToken(user.getEmail())
        TodolistSubjectChangeRequest request = makeTodolistSubjectChangeRequest("subject")

        when:
        ResultActions result = mvc.perform(put("/todolist/subject/{subjectId}", subject.getTodolistSubjectId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())

        then:
        result.andExpect(status().is(201))
    }

    def "test changeTodolistSubjectOrder" () {
        given:
        TodolistSubject subject = TodolistSubject.builder()
                .todolist(todolist)
                .subject("subject")
                .value(100)
                .build()
        subject = todolistSubjectRepository.save(subject)

        String token = jwtTokenProvider.generateAccessToken(user.getEmail())
        TodolistSubjectOrderRequest request = makeTodolistSubjectOrderRequest(order)

        when:
        ResultActions result = mvc.perform(put("/todolist/subject-order/{subjectId}", subject.getTodolistSubjectId())
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

    def "test deleteTodolistSubject" () {
        given:
        TodolistSubject subject = TodolistSubject.builder()
                .todolist(todolist)
                .subject("subject")
                .value(100)
                .build()
        subject = todolistSubjectRepository.save(subject)

        String token = jwtTokenProvider.generateAccessToken(user.getEmail())

        when:
        ResultActions result = mvc.perform(delete("/todolist/subject/{subjectId}", subject.getTodolistSubjectId())
                .header("Authorization", "Bearer " + token))
                .andDo(print())

        then:
        result.andExpect(status().is(204))
    }

}