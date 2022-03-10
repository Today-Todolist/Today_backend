package todolist.today.today.domain.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import todolist.today.today.domain.todolist.dto.todolist.MyCalendarFutureResponse;
import todolist.today.today.domain.todolist.dto.todolist.MyCalendarPastResponse;

import java.util.List;

@Getter
@AllArgsConstructor
public class MyCalendarResponse {

    private List<MyCalendarPastResponse> past;
    private List<MyCalendarFutureResponse> future;

}
