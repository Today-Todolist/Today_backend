package todolist.today.today.domain.todolist.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.today.today.domain.todolist.dao.CustomTodolistSubjectRepositoryImpl;
import todolist.today.today.domain.todolist.dao.TodolistRepository;
import todolist.today.today.domain.todolist.dao.TodolistSubjectRepository;
import todolist.today.today.domain.todolist.domain.Todolist;
import todolist.today.today.domain.todolist.domain.TodolistSubject;
import todolist.today.today.domain.todolist.dto.request.TodolistSubjectCreateRequest;
import todolist.today.today.domain.user.dao.UserRepository;
import todolist.today.today.domain.user.domain.User;
import todolist.today.today.domain.user.exception.UserNotFoundException;

import java.time.LocalDate;

import static todolist.today.today.global.dto.LocalDateUtil.convert;

@Service
@RequiredArgsConstructor
@Transactional
public class TodolistSubjectService {

    private final UserRepository userRepository;
    private final TodolistRepository todolistRepository;
    private final CustomTodolistSubjectRepositoryImpl customTodolistSubjectRepository;
    private final TodolistSubjectRepository todolistSubjectRepository;
    private final TodolistSortService todolistSortService;

    public void makeTodolistSubject(String userId, TodolistSubjectCreateRequest request) {
        LocalDate date = convert(request.getDate());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Todolist todolist = todolistRepository.findByUserAndDate(user, date)
                .orElseGet(() ->
                        todolistRepository.save(Todolist.builder()
                                .user(user)
                                .date(date)
                                .build()));
        int value = customTodolistSubjectRepository.getTodolistSubjectLastValue(todolist.getTodolistId());
        if (value >= 2147483500) value = todolistSortService.sortTodolistSubject(todolist);

        TodolistSubject subject = TodolistSubject.builder()
                .todolist(todolist)
                .subject(request.getSubject())
                .value(value)
                .build();
        todolistSubjectRepository.save(subject);
    }

}
