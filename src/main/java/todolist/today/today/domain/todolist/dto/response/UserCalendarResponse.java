package todolist.today.today.domain.todolist.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import todolist.today.today.domain.todolist.dto.response.todolist.UserCalendarFutureResponse;
import todolist.today.today.domain.todolist.dto.response.todolist.UserCalendarPastResponse;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserCalendarResponse {

    private List<UserCalendarPastResponse> past;
    private List<UserCalendarFutureResponse> future;

}
