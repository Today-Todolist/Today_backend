package todolist.today.today.domain.todolist.dto.todolist;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCalendarFutureResponse {

    private int day;
    private int todolists;

}
