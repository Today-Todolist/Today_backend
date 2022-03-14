package todolist.today.today.domain.todolist.dto.response;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

import static todolist.today.today.global.dto.LocalDateUtil.convert;

@Getter
public class TodolistRecordResponse {

    private String date;
    private int successAmount;
    private int failAmount;

    public TodolistRecordResponse(LocalDate date, List<Boolean> isSuccess) {
        this.date = convert(date);
        if (isSuccess.get(0) != null) {
            this.successAmount = Long.valueOf(isSuccess.stream().filter(s -> s).count()).intValue();
            this.failAmount = Long.valueOf(isSuccess.stream().filter(s -> !s).count()).intValue();
        } else {
            this.successAmount = 0;
            this.failAmount = 0;
        }
    }

}
