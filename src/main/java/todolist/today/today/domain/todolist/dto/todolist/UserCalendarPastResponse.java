package todolist.today.today.domain.todolist.dto.todolist;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCalendarPastResponse {

    private int day;

    @JsonProperty("is_success")
    private boolean isSuccess;

}
