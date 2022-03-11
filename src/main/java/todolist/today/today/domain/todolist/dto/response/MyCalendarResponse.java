package todolist.today.today.domain.todolist.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import todolist.today.today.domain.todolist.dto.response.todolist.MyCalendarFutureResponse;
import todolist.today.today.domain.todolist.dto.response.todolist.MyCalendarPastResponse;

import java.util.List;

@Getter
@AllArgsConstructor
public class MyCalendarResponse {

    private List<MyCalendarPastResponse> past;
    private List<MyCalendarFutureResponse> future;

}
