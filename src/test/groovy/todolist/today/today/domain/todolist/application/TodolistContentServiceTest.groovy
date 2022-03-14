package todolist.today.today.domain.todolist.application

import spock.lang.Specification
import todolist.today.today.domain.todolist.dao.CustomTodolistContentRepositoryImpl
import todolist.today.today.domain.todolist.dao.TodolistContentRepository
import todolist.today.today.domain.todolist.dao.TodolistSubjectRepository
import todolist.today.today.domain.todolist.domain.Todolist
import todolist.today.today.domain.todolist.domain.TodolistContent
import todolist.today.today.domain.todolist.domain.TodolistSubject
import todolist.today.today.domain.todolist.dto.request.TodolistContentChangeRequest
import todolist.today.today.domain.todolist.dto.request.TodolistContentCreateRequest
import todolist.today.today.domain.todolist.dto.request.TodolistContentOrderRequest
import todolist.today.today.domain.todolist.exception.TodolistContentNotFoundException
import todolist.today.today.domain.todolist.exception.TodolistContentOrderException
import todolist.today.today.domain.todolist.exception.TodolistSubjectNotFoundException
import todolist.today.today.domain.user.domain.User

import static todolist.today.today.RequestUtil.*

class TodolistContentServiceTest extends Specification {

    private TodolistContentService todolistContentService = Stub(TodolistContentService)
    private TodolistSubjectRepository todolistSubjectRepository = Stub(TodolistSubjectRepository)
    private CustomTodolistContentRepositoryImpl customTodolistContentRepository = Stub(CustomTodolistContentRepositoryImpl)
    private TodolistContentRepository todolistContentRepository = Stub(TodolistContentRepository)
    private TodolistSortService todolistSortService = Stub(TodolistSortService)

    def setup() {
        todolistContentService = new TodolistContentService(todolistSubjectRepository,
                customTodolistContentRepository,
                todolistContentRepository,
                todolistSortService)
    }

    def "test makeTodolistSubject" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID SUBJECT_ID = UUID.randomUUID()
        TodolistContentCreateRequest request = makeTodolistContentCreateRequest(SUBJECT_ID.toString(), "")

        TodolistSubject subject = Stub(TodolistSubject)
        Todolist todolist = Stub(Todolist)
        User user = Stub(User)

        todolistSubjectRepository.findById(SUBJECT_ID) >> Optional.of(subject)
        subject.getTodolist() >> todolist
        todolist.getUser() >> user
        user.getEmail() >> USER_ID

        customTodolistContentRepository.getTodolistContentLastValue(SUBJECT_ID) >> value

        when:
        todolistContentService.makeTodolistSubject(USER_ID, request)

        then:
        noExceptionThrown()

        where:
        value << [100, 2147483500]
    }

    def "test makeTodolistSubject TodolistSubjectNotFoundException" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID SUBJECT_ID = UUID.randomUUID()
        TodolistContentCreateRequest request = makeTodolistContentCreateRequest(SUBJECT_ID.toString(), "")

        todolistSubjectRepository.findById(SUBJECT_ID) >> Optional.empty()

        when:
        todolistContentService.makeTodolistSubject(USER_ID, request)

        then:
        thrown(TodolistSubjectNotFoundException)
    }

    def "test changeTodolistContent" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID CONTENT_ID = UUID.randomUUID()

        TodolistContentChangeRequest request = makeTodolistContentChangeRequest("")

        TodolistContent content = Stub(TodolistContent)
        TodolistSubject subject = Stub(TodolistSubject)
        Todolist todolist = Stub(Todolist)
        User user = Stub(User)

        todolistContentRepository.findById(CONTENT_ID) >> Optional.of(content)
        content.getTodolistSubject() >> subject
        subject.getTodolist() >> todolist
        todolist.getUser() >> user
        user.getEmail() >> USER_ID

        when:
        todolistContentService.changeTodolistContent(USER_ID, CONTENT_ID.toString(), request)

        then:
        noExceptionThrown()
    }

    def "test changeTodolistContent TodolistContentNotFoundException" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID CONTENT_ID = UUID.randomUUID()

        TodolistContentChangeRequest request = makeTodolistContentChangeRequest("")

        todolistContentRepository.findById(CONTENT_ID) >> Optional.empty()

        when:
        todolistContentService.changeTodolistContent(USER_ID, CONTENT_ID.toString(), request)

        then:
        thrown(TodolistContentNotFoundException)
    }

    def "test changeTodolistContentOrder" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID SUBJECT_ID = UUID.randomUUID()
        final UUID CONTENT_ID = UUID.randomUUID()

        TodolistContentOrderRequest request = makeTodolistContentOrderRequest(1)

        TodolistContent content = Stub(TodolistContent)
        TodolistSubject subject = Stub(TodolistSubject)
        Todolist todolist = Stub(Todolist)
        User user = Stub(User)

        todolistContentRepository.findById(CONTENT_ID) >> Optional.of(content)
        content.getTodolistSubject() >> subject
        subject.getTodolist() >> todolist
        subject.getTodolistSubjectId() >> SUBJECT_ID
        todolist.getUser() >> user
        user.getEmail() >> USER_ID

        ArrayList<Integer> values = new ArrayList<>()
        for(int i=0; i<2; i++) {
            values.add(value)
            value += add
        }

        customTodolistContentRepository
                .getTodolistContentValueByOrder(SUBJECT_ID, CONTENT_ID, 1) >> values

        when:
        todolistContentService.changeTodolistContentOrder(USER_ID, CONTENT_ID.toString(), request)

        then:
        noExceptionThrown()

        where:
        add | value
        0 | 25
        1 | 25
        0 | 2147483500
    }

    def "test changeTodolistContentOrder TodolistContentNotFoundException" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID CONTENT_ID = UUID.randomUUID()

        TodolistContentOrderRequest request = makeTodolistContentOrderRequest(1)

        todolistContentRepository.findById(CONTENT_ID) >> Optional.empty()

        when:
        todolistContentService.changeTodolistContentOrder(USER_ID, CONTENT_ID.toString(), request)

        then:
        thrown(TodolistContentNotFoundException)
    }

    def "test changeTodolistContentOrder TodolistContentOrderException" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID SUBJECT_ID = UUID.randomUUID()
        final UUID CONTENT_ID = UUID.randomUUID()

        TodolistContentOrderRequest request = makeTodolistContentOrderRequest(1)

        TodolistContent content = Stub(TodolistContent)
        TodolistSubject subject = Stub(TodolistSubject)
        Todolist todolist = Stub(Todolist)
        User user = Stub(User)

        todolistContentRepository.findById(CONTENT_ID) >> Optional.of(content)
        content.getTodolistSubject() >> subject
        subject.getTodolist() >> todolist
        subject.getTodolistSubjectId() >> SUBJECT_ID
        todolist.getUser() >> user
        user.getEmail() >> USER_ID

        customTodolistContentRepository
                .getTodolistContentValueByOrder(SUBJECT_ID, CONTENT_ID, 1) >> { throw new IndexOutOfBoundsException() }

        when:
        todolistContentService.changeTodolistContentOrder(USER_ID, CONTENT_ID.toString(), request)

        then:
        thrown(TodolistContentOrderException)
    }

    def "test deleteTodolistContent" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID CONTENT_ID = UUID.randomUUID()

        TodolistContent content = Stub(TodolistContent)
        TodolistSubject subject = Stub(TodolistSubject)
        Todolist todolist = Stub(Todolist)
        User user = Stub(User)

        todolistContentRepository.findById(CONTENT_ID) >> Optional.of(content)
        content.getTodolistSubject() >> subject
        subject.getTodolist() >> todolist
        todolist.getUser() >> user
        user.getEmail() >> USER_ID

        when:
        todolistContentService.deleteTodolistContent(USER_ID, CONTENT_ID.toString())

        then:
        noExceptionThrown()
    }

    def "test deleteTodolistContent TodolistContentNotFoundException" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID CONTENT_ID = UUID.randomUUID()

        todolistContentRepository.findById(CONTENT_ID) >> Optional.empty()

        when:
        todolistContentService.deleteTodolistContent(USER_ID, CONTENT_ID.toString())

        then:
        thrown(TodolistContentNotFoundException)
    }

}