package todolist.today.today.domain.todolist.application

import spock.lang.Specification
import todolist.today.today.domain.todolist.dao.CustomTodolistSubjectRepositoryImpl
import todolist.today.today.domain.todolist.dao.TodolistRepository
import todolist.today.today.domain.todolist.dao.TodolistSubjectRepository
import todolist.today.today.domain.todolist.domain.Todolist
import todolist.today.today.domain.todolist.domain.TodolistSubject
import todolist.today.today.domain.todolist.dto.request.TodolistSubjectChangeRequest
import todolist.today.today.domain.todolist.dto.request.TodolistSubjectCreateRequest
import todolist.today.today.domain.todolist.dto.request.TodolistSubjectOrderRequest
import todolist.today.today.domain.todolist.exception.TodolistSubjectNotFoundException
import todolist.today.today.domain.todolist.exception.TodolistSubjectOrderException
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.domain.User
import todolist.today.today.domain.user.exception.UserNotFoundException

import java.time.LocalDate

import static todolist.today.today.RequestUtil.*
import static todolist.today.today.global.dto.LocalDateUtil.convert

class TodolistSubjectServiceTest extends Specification {

    TodolistSubjectService todolistSubjectService
    UserRepository userRepository = Stub()
    TodolistRepository todolistRepository = Stub()
    CustomTodolistSubjectRepositoryImpl customTodolistSubjectRepository = Stub()
    TodolistSubjectRepository todolistSubjectRepository = Stub()
    TodolistSortService todolistSortService = Stub()

    def setup() {
        todolistSubjectService = new TodolistSubjectService(userRepository,
                todolistRepository,
                customTodolistSubjectRepository,
                todolistSubjectRepository,
                todolistSortService)
    }

    def "test makeTodolistSubject" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final LocalDate DATE = LocalDate.now()
        TodolistSubjectCreateRequest request = makeTodolistSubjectCreateRequest(convert(DATE), "")

        Todolist todolist = Stub()
        User user = Stub()

        todolist.getTodolistId() >> UUID.randomUUID()
        userRepository.findById(USER_ID) >> Optional.of(user)
        todolistRepository.findByUserAndDate(_, DATE) >> (exists ? Optional.of(todolist) : Optional.empty())
        todolistRepository.save(_) >> todolist

        customTodolistSubjectRepository.getTodolistSubjectLastValue(_) >> value
        todolistSortService.sortTodolistSubject(_) >> 1

        when:
        todolistSubjectService.makeTodolistSubject(USER_ID, request)

        then:
        noExceptionThrown()

        where:
        value | exists
        1 | true
        2147483500 | true
        1 | false
    }

    def "test makeTodolistSubject UserNotFoundException" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final LocalDate DATE = LocalDate.now()
        TodolistSubjectCreateRequest request = makeTodolistSubjectCreateRequest(convert(DATE), "")

        userRepository.findById(USER_ID) >> Optional.empty()

        when:
        todolistSubjectService.makeTodolistSubject(USER_ID, request)

        then:
        thrown(UserNotFoundException)
    }

    def "test changeTodolistSubject" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID SUBJECT_ID = UUID.randomUUID()
        TodolistSubjectChangeRequest request = makeTodolistSubjectChangeRequest("")

        TodolistSubject subject = Stub()
        Todolist todolist = Stub()
        User user = Stub()

        todolistSubjectRepository.findById(SUBJECT_ID) >> Optional.of(subject)
        subject.getTodolist() >> todolist
        todolist.getUser() >> user
        user.getEmail() >> USER_ID

        when:
        todolistSubjectService.changeTodolistSubject(USER_ID, SUBJECT_ID.toString(), request)

        then:
        noExceptionThrown()
    }

    def "test changeTodolistSubject TodolistSubjectNotFoundException" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID SUBJECT_ID = UUID.randomUUID()
        TodolistSubjectChangeRequest request = makeTodolistSubjectChangeRequest("")


        todolistSubjectRepository.findById(SUBJECT_ID) >> Optional.empty()

        when:
        todolistSubjectService.changeTodolistSubject(USER_ID, SUBJECT_ID.toString(), request)

        then:
        thrown(TodolistSubjectNotFoundException)
    }

    def "test changeTemplateSubjectOrder" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID TODOLIST_ID = UUID.randomUUID()
        final UUID SUBJECT_ID = UUID.randomUUID()
        TodolistSubjectOrderRequest request = makeTodolistSubjectOrderRequest(1)

        TodolistSubject subject = Stub()
        Todolist todolist = Stub()
        User user = Stub()

        todolistSubjectRepository.findById(SUBJECT_ID) >> Optional.of(subject)
        subject.getTodolist() >> todolist
        todolist.getUser() >> user
        user.getEmail() >> USER_ID
        todolist.getTodolistId() >> TODOLIST_ID

        ArrayList<Integer> values = new ArrayList<>()
        for(int i=0; i<2; i++) {
            values.add(value)
            value += add
        }

        customTodolistSubjectRepository
                .getTodolistSubjectValueByOrder(TODOLIST_ID, SUBJECT_ID, 1) >> values

        when:
        todolistSubjectService.changeTemplateSubjectOrder(USER_ID, SUBJECT_ID.toString(), request)

        then:
        noExceptionThrown()

        where:
        add | value
        0 | 25
        1 | 25
        0 | 2147483500
    }

    def "test changeTemplateSubjectOrder TodolistSubjectOrderException" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID TODOLIST_ID = UUID.randomUUID()
        final UUID SUBJECT_ID = UUID.randomUUID()
        TodolistSubjectOrderRequest request = makeTodolistSubjectOrderRequest(1)

        TodolistSubject subject = Stub()
        Todolist todolist = Stub()
        User user = Stub()

        todolistSubjectRepository.findById(SUBJECT_ID) >> Optional.of(subject)
        subject.getTodolist() >> todolist
        todolist.getUser() >> user
        user.getEmail() >> USER_ID
        todolist.getTodolistId() >> TODOLIST_ID

        customTodolistSubjectRepository
                .getTodolistSubjectValueByOrder(TODOLIST_ID, SUBJECT_ID, 1) >> { throw new IndexOutOfBoundsException() }

        when:
        todolistSubjectService.changeTemplateSubjectOrder(USER_ID, SUBJECT_ID.toString(), request)

        then:
        thrown(TodolistSubjectOrderException)
    }

    def "test deleteTemplateSubject" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID SUBJECT_ID = UUID.randomUUID()

        TodolistSubject subject = Stub()
        Todolist todolist = Stub()
        User user = Stub()

        todolistSubjectRepository.findById(SUBJECT_ID) >> Optional.of(subject)
        subject.getTodolist() >> todolist
        todolist.getUser() >> user
        user.getEmail() >> USER_ID

        when:
        todolistSubjectService.deleteTemplateSubject(USER_ID, SUBJECT_ID.toString())

        then:
        noExceptionThrown()
    }

    def "test deleteTemplateSubject TodolistSubjectNotFoundException" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID SUBJECT_ID = UUID.randomUUID()

        todolistSubjectRepository.findById(SUBJECT_ID) >> Optional.empty()

        when:
        todolistSubjectService.deleteTemplateSubject(USER_ID, SUBJECT_ID.toString())

        then:
        thrown(TodolistSubjectNotFoundException)
    }

}