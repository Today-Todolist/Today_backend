package todolist.today.today.domain.todolist.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class TodolistContentChangeRequest {

    @NotEmpty
    @Size(min = 1, max = 31)
    private String content;

}
