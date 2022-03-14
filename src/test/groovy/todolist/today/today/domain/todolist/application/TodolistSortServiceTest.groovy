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
        todolist.getTodolistSubjects() >> Arrays.asList(new TodolistSubject(), new TodolistSubject())

        when:
        todolistSortService.sortTodolistSubject(todolist)

        then:
        noExceptionThrown()
    }

    def "test sortTodolistContent" () {
        given:
        TodolistSubject subject = Stub(TodolistSubject)
        subject.getTodolistContents() >> Arrays.asList(new TodolistContent(), new TodolistContent())

        when:
        todolistSortService.sortTodolistContent(subject)

        then:
        noExceptionThrown()
    }

}