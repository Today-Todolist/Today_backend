package todolist.today.today.domain.todolist.application

import spock.lang.Specification
import todolist.today.today.domain.todolist.dao.TodolistContentRepository
import todolist.today.today.domain.todolist.domain.Todolist
import todolist.today.today.domain.todolist.domain.TodolistContent
import todolist.today.today.domain.todolist.domain.TodolistSubject
import todolist.today.today.domain.todolist.exception.TodolistContentNotFoundException
import todolist.today.today.domain.user.domain.User

import java.time.LocalDate

class TodolistClearServiceTest extends Specification {

    private TodolistClearService todolistClearService
    private TodolistContentRepository todolistContentRepository = Stub(TodolistContentRepository)

    def setup() {
        todolistClearService = new TodolistClearService(todolistContentRepository)
    }

    def "test changeIsSuccess" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID CONTENT_ID = UUID.randomUUID()

        User user = User.builder()
                .email(USER_ID)
                .password("")
                .nickname("")
                .profile("")
                .build()

        Todolist todolist = Todolist.builder()
                .user(user)
                .date(LocalDate.now())
                .build()

        TodolistSubject subject = TodolistSubject.builder()
                .todolist(todolist)
                .subject("subject")
                .value(100)
                .build()

        TodolistContent content = TodolistContent.builder()
                .todolistSubject(subject)
                .content("content")
                .value(2000)
                .build()

        todolistContentRepository.findById(CONTENT_ID) >> Optional.of(content)

        when:
        todolistClearService.changeIsSuccess(USER_ID, CONTENT_ID.toString(), true)

        then:
        noExceptionThrown()
    }

    def "test changeIsSuccess TodolistContentNotFoundException" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final UUID CONTENT_ID = UUID.randomUUID()

        todolistContentRepository.findById(CONTENT_ID) >> Optional.empty()

        when:
        todolistClearService.changeIsSuccess(USER_ID, CONTENT_ID.toString(), true)

        then:
        thrown(TodolistContentNotFoundException)
    }

}