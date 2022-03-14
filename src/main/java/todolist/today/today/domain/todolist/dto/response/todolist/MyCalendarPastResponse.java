package todolist.today.today.domain.todolist.dto.response.todolist;

import lombok.Getter;

import java.util.List;

@Getter
public class MyCalendarPastResponse {

    private int day;
    private Boolean isSuccess;

    public MyCalendarPastResponse(int day, List<Boolean> isSuccess) {
        this.day = day;
        if (isSuccess.get(0) != null) {
            this.isSuccess = !isSuccess.contains(Boolean.FALSE);
        }
    }

}
