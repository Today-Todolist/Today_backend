package todolist.today.today.domain.todolist.dto.response.todolist.subject;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class TodolistContentTodolistSubjectResponse {

    private String id;
    private String value;
    private Boolean isSuccess;

    public TodolistContentTodolistSubjectResponse(UUID id, String value, List<Boolean> isSuccess) {
        this.id = id == null ? null : id.toString();
        this.value = value;
        if (isSuccess.get(0) != null) {
            this.isSuccess = !isSuccess.contains(Boolean.FALSE);
        }
    }

}
