package todolist.today.today.domain.todolist.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@NoArgsConstructor
public class TodolistContentOrderRequest {

    @Min(0) @Max(20)
    private int order;

}
