package todolist.today.today.domain.todolist.dto.response;

import lombok.Getter;
import todolist.today.today.domain.todolist.dto.response.todolist.content.TodolistContentTodolistContentResponse;
import todolist.today.today.domain.todolist.dto.response.todolist.subject.TodolistContentTodolistSubjectResponse;

import java.util.Collections;
import java.util.List;

@Getter
public class TodolistContentResponse {

    private TodolistContentTodolistSubjectResponse subject;
    private List<TodolistContentTodolistContentResponse> content;

    public TodolistContentResponse(TodolistContentTodolistSubjectResponse subject, List<TodolistContentTodolistContentResponse> content) {
        this.subject = subject;
        this.content = content.get(0).getId() != null ? content : Collections.emptyList();
    }

}
