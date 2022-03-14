package todolist.today.today.domain.todolist.application

import spock.lang.Specification
import todolist.today.today.domain.todolist.dao.CustomTodolistRepositoryImpl
import todolist.today.today.domain.todolist.dao.TodolistRepository

import java.time.LocalDate

class TodolistInfoServiceTest extends Specification {

    private TodolistInfoService todolistInfoService
    private CustomTodolistRepositoryImpl customTodolistRepository = Stub(CustomTodolistRepositoryImpl)
    private TodolistRepository todolistRepository = Stub(TodolistRepository)

    def setup() {
        todolistInfoService = new TodolistInfoService(customTodolistRepository, todolistRepository)
    }

    def "test getTodolistRecord" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final LocalDate START = LocalDate.now().minusDays(1)
        final LocalDate END = LocalDate.now().plusDays(1)

        todolistRepository.countByUserEmail(USER_ID) >> count

        customTodolistRepository.getTodolistRecord(USER_ID, START, END) >> Collections.emptyList()

        when:
        todolistInfoService.getTodolistRecord(USER_ID, START, END)

        then:
        noExceptionThrown()

        where:
        count << [0L, 1L]
    }

    def "test getMyCalendar" () {
        when:
        todolistInfoService.getMyCalendar("", "2020-03")

        then:
        noExceptionThrown()
    }

    def "test getUserCalendar" () {
        when:
        todolistInfoService.getUserCalendar("", "2020-03")

        then:
        noExceptionThrown()
    }

    def "test getTodolist" () {
        when:
        todolistInfoService.getTodolist("", LocalDate.now())

        then:
        noExceptionThrown()
    }

}