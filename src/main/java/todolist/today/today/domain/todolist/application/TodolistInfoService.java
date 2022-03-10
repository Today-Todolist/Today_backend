package todolist.today.today.domain.todolist.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.today.today.domain.todolist.dao.CustomTodolistRepositoryImpl;
import todolist.today.today.domain.todolist.dao.TodolistRepository;
import todolist.today.today.domain.todolist.dto.TodolistRecordResponse;
import todolist.today.today.global.dto.response.PagingResponse;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

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

}
