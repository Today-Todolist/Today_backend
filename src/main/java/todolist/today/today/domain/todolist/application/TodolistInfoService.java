package todolist.today.today.domain.todolist.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.today.today.domain.todolist.dao.CustomTodolistRepositoryImpl;
import todolist.today.today.domain.todolist.dao.TodolistRepository;
import todolist.today.today.domain.todolist.dto.response.MyCalendarResponse;
import todolist.today.today.domain.todolist.dto.response.TodolistRecordResponse;
import todolist.today.today.domain.todolist.dto.response.UserCalendarResponse;
import todolist.today.today.domain.todolist.dto.response.todolist.MyCalendarFutureResponse;
import todolist.today.today.domain.todolist.dto.response.todolist.MyCalendarPastResponse;
import todolist.today.today.domain.todolist.dto.response.todolist.UserCalendarFutureResponse;
import todolist.today.today.domain.todolist.dto.response.todolist.UserCalendarPastResponse;
import todolist.today.today.global.dto.response.PagingResponse;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static todolist.today.today.global.dto.LocalDateUtil.convert;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodolistInfoService {

    private final CustomTodolistRepositoryImpl customTodolistRepository;
    private final TodolistRepository todolistRepository;

    public PagingResponse<TodolistRecordResponse> getTodolistRecord(String userId, LocalDate startDate, LocalDate endDate) {
        long totalElements = todolistRepository.countByUserEmail(userId);

        List<TodolistRecordResponse> response;
        if (totalElements >= 1) response = customTodolistRepository.getTodolistRecord(userId, startDate, endDate);
        else response = Collections.emptyList();

        return new PagingResponse<>(totalElements, response);
    }

    public MyCalendarResponse getMyCalendar(String userId, String date) {
        List<MyCalendarPastResponse> past = customTodolistRepository.getMyCalendarPast(userId, convert(date + "-01"));
        List<MyCalendarFutureResponse> future = customTodolistRepository.getMyCalendarFuture(userId, convert(date + "-31"));
        return new MyCalendarResponse(past, future);
    }

    public UserCalendarResponse getUserCalendar(String userId, String date) {
        List<UserCalendarPastResponse> past = customTodolistRepository.getUserCalendarPast(userId, convert(date + "-01"));
        List<UserCalendarFutureResponse> future = customTodolistRepository.getUserCalendarFuture(userId, convert(date + "-31"));
        return new UserCalendarResponse(past, future);
    }

}
