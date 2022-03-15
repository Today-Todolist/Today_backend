package todolist.today.today.domain.todolist.application

import spock.lang.Specification
import todolist.today.today.domain.todolist.dao.TodolistContentRepository
import todolist.today.today.domain.todolist.dao.TodolistSubjectRepository
import todolist.today.today.domain.todolist.domain.Todolist
import todolist.today.today.domain.todolist.domain.TodolistContent
import todolist.today.today.domain.todolist.domain.TodolistSubject

class TodolistSortServiceTest extends Specification {

    private TodolistSortService todolistSortService
    private TodolistSubjectRepository todolistSubjectRepository = Stub(TodolistSubjectRepository)
    private TodolistContentRepository todolistContentRepository = Stub(TodolistContentRepository)

    def setup() {
        todolistSortService = new TodolistSortService(todolistSubjectRepository,
                todolistContentRepository)
    }

    def "test sortTodolistSubject" () {
        given:
        Todolist todolist = Stub(Todolist)
        TodolistSubject subject1 = TodolistSubject.builder()
                .subject("subject1")
                .value(200)
                .build()

        TodolistSubject subject2 = TodolistSubject.builder()
                .subject("subject2")
                .value(100)
                .build()

        TodolistSubject subject3 = TodolistSubject.builder()
                .subject("subject3")
                .value(300)
                .build()

        todolist.getTodolistSubjects() >> Arrays.asList(subject1, subject2, subject3)

        when:
        todolistSortService.sortTodolistSubject(todolist)

        then:
        noExceptionThrown()
    }

    def "test sortTodolistContent" () {
        given:
        TodolistSubject subject = Stub(TodolistSubject)
        TodolistContent content1 = TodolistContent.builder()
                .content("content1")
                .value(200)
                .build()

        TodolistContent content2 = TodolistContent.builder()
                .content("content2")
                .value(100)
                .build()

        TodolistContent content3 = TodolistContent.builder()
                .content("content3")
                .value(300)
                .build()

        subject.getTodolistContents() >> Arrays.asList(content1, content2, content3)

        when:
        todolistSortService.sortTodolistContent(subject)

        then:
        noExceptionThrown()
    }

}