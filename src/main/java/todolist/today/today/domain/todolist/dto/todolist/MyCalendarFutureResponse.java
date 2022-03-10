package todolist.today.today.domain.todolist.dto.todolist;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MyCalendarFutureResponse {

    private int day;
    private int todolists;

    public MyCalendarFutureResponse(LocalDate date, int todolists) {
        this.day = date.getMonthValue();
        this.todolists = todolists;
    }

}
