package todolist.today.today.domain.todolist.dto.response;

import lombok.Getter;

import java.time.LocalDate;

import static todolist.today.today.global.dto.LocalDateUtil.convert;

@Getter
public class TodolistRecordResponse {

    private String date;
    private int successAmount;
    private int failAmount;

    public TodolistRecordResponse(LocalDate date, int successAmount, int failAmount) {
        this.date = convert(date);
        this.successAmount = successAmount;
        this.failAmount = failAmount;
    }

}
