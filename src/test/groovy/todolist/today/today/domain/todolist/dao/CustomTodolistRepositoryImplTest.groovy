package todolist.today.today.domain.todolist.dao

import com.fasterxml.jackson.databind.ObjectMapper
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import spock.lang.Specification
import todolist.today.today.domain.todolist.domain.Todolist
import todolist.today.today.domain.todolist.dto.response.TodolistContentResponse
import todolist.today.today.domain.todolist.dto.response.TodolistRecordResponse
import todolist.today.today.domain.todolist.dto.response.todolist.MyCalendarFutureResponse
import todolist.today.today.domain.todolist.dto.response.todolist.MyCalendarPastResponse
import todolist.today.today.domain.todolist.dto.response.todolist.UserCalendarFutureResponse
import todolist.today.today.domain.todolist.dto.response.todolist.UserCalendarPastResponse
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.domain.User
import todolist.today.today.global.config.JpaAuditingConfig

import javax.persistence.EntityManager
import java.time.LocalDate

@DataJpaTest
@Import(JpaAuditingConfig)
class CustomTodolistRepositoryImplTest extends Specification {

    @Autowired
    private EntityManager em
    private JPAQueryFactory jpaQueryFactory
    private CustomTodolistRepositoryImpl customTodolistRepository

    @Autowired
    private UserRepository userRepository

    @Autowired
    private TodolistRepository todolistRepository

    private User user

    def setup() {
        jpaQueryFactory = new JPAQueryFactory(em)
        customTodolistRepository = new CustomTodolistRepositoryImpl(new JPAQueryFactory(em))

        user = User.builder()
                .email("today043149@gmail.com")
                .password("")
                .nickname("")
                .profile("")
                .build()
        user = userRepository.save(user)
    }

    def "test getTodolistRecord" () {
        given:
        Todolist todolist = Todolist.builder()
                .user(user)
                .date(LocalDate.now())
                .build()
        todolistRepository.save(todolist)

        when:
        List<TodolistRecordResponse> response = customTodolistRepository.getTodolistRecord(user.getEmail(), LocalDate.now().minusDays(100), LocalDate.now().plusDays(100))

        then:
        response.size() == 1
    }

    def "test getMyCalendarPast" () {
        given:
        Todolist todolist = Todolist.builder()
                .user(user)
                .date(LocalDate.now().minusDays(1))
                .build()
        todolistRepository.save(todolist)

        when:
        List<MyCalendarPastResponse> response = customTodolistRepository.getMyCalendarPast(user.getEmail(), LocalDate.now().minusDays(2))

        then:
        response.size() == 0
    }

    def "test getMyCalendarFuture" () {
        given:
        Todolist todolist = Todolist.builder()
                .user(user)
                .date(LocalDate.now().plusDays(1))
                .build()
        todolistRepository.save(todolist)

        when:
        List<MyCalendarFutureResponse> response = customTodolistRepository.getMyCalendarFuture(user.getEmail(), LocalDate.now().plusDays(2))

        then:
        response.size() == 0
    }

    def "test getUserCalendarPast" () {
        given:
        Todolist todolist = Todolist.builder()
                .user(user)
                .date(LocalDate.now().minusDays(1))
                .build()
        todolistRepository.save(todolist)

        when:
        List<UserCalendarPastResponse> response = customTodolistRepository.getUserCalendarPast(user.getEmail(), LocalDate.now().minusDays(2))

        then:
        response.size() == 0
    }

    def "test getUserCalendarFuture" () {
        given:
        Todolist todolist = Todolist.builder()
                .user(user)
                .date(LocalDate.now().plusDays(1))
                .build()
        todolistRepository.save(todolist)

        when:
        List<UserCalendarFutureResponse> response = customTodolistRepository.getUserCalendarFuture(user.getEmail(), LocalDate.now().plusDays(2))

        then:
        response.size() == 0
    }

    def "test getTodolist" () {
        given:
        Todolist todolist = Todolist.builder()
                .user(user)
                .date(LocalDate.now())
                .build()
        todolistRepository.save(todolist)

        when:
        TodolistContentResponse response = customTodolistRepository.getTodolist(user.getEmail(), LocalDate.now())
        ObjectMapper objectMapper = new ObjectMapper()
        println "테스트 " + objectMapper.writeValueAsString(response)

        then:
        response != null
    }

}