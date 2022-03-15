package todolist.today.today.domain.todolist.dto.response.todolist;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserCalendarPastResponse {

    private int day;
    private Boolean isSuccess;

    public UserCalendarPastResponse(int day, List<Boolean> isSuccess) {
        this.day = day;
        if (isSuccess.get(0) != null) {
            this.isSuccess = !isSuccess.contains(Boolean.FALSE);
        }
    }

}
