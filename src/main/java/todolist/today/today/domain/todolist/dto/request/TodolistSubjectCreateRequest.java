package todolist.today.today.domain.todolist.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class TodolistSubjectCreateRequest {

    @NotEmpty
    @Pattern(regexp = "\\d\\d\\d\\d-\\d\\d-\\d\\d")
    private String date;

    @NotEmpty
    @Size(min = 1, max = 31)
    private String subject;

}
