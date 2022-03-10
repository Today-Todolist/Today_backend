package todolist.today.today.domain.todolist.dto.todolist;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MyCalendarPastResponse {

    private int day;

    @JsonProperty("is_success")
    private boolean isSuccess;

    public MyCalendarPastResponse(LocalDate date, boolean isSuccess) {
        this.day = date.getMonthValue();
        this.isSuccess = isSuccess;
    }

}
