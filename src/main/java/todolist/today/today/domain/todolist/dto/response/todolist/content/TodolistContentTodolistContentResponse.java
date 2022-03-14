package todolist.today.today.domain.todolist.dto.response.todolist.content;

import lombok.Getter;

import java.util.UUID;

@Getter
public class TodolistContentTodolistContentResponse {

    private String id;
    private String value;
    private Boolean isSuccess;

    public TodolistContentTodolistContentResponse(UUID id, String value, boolean isSuccess) {
        this.id = id == null ? null : id.toString();
        this.value = value;
        this.isSuccess = isSuccess;
    }

}