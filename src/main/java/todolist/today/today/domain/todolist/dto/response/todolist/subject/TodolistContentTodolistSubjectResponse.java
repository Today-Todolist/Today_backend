package todolist.today.today.domain.todolist.dto.response.todolist.subject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.UUID;

@Getter
public class TodolistContentTodolistSubjectResponse {

    private String id;
    private String value;

    @JsonProperty("is_success")
    private boolean isSuccess;

    public TodolistContentTodolistSubjectResponse(UUID id, String value, boolean isSuccess) {
        this.id = id.toString();
        this.value = value;
        this.isSuccess = isSuccess;
    }

}
