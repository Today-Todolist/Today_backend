package todolist.today.today.domain.todolist.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.today.today.domain.template.exception.TemplateSubjectNotFoundException;
import todolist.today.today.domain.template.exception.TemplateSubjectOrderException;
import todolist.today.today.domain.todolist.dao.CustomTodolistSubjectRepositoryImpl;
import todolist.today.today.domain.todolist.dao.TodolistRepository;
import todolist.today.today.domain.todolist.dao.TodolistSubjectRepository;
import todolist.today.today.domain.todolist.domain.Todolist;
import todolist.today.today.domain.todolist.domain.TodolistSubject;
import todolist.today.today.domain.todolist.dto.request.TodolistSubjectChangeRequest;
import todolist.today.today.domain.todolist.dto.request.TodolistSubjectCreateRequest;
import todolist.today.today.domain.todolist.dto.request.TodolistSubjectOrderRequest;
import todolist.today.today.domain.todolist.exception.TodolistSubjectNotFoundException;
import todolist.today.today.domain.user.dao.UserRepository;
import todolist.today.today.domain.user.domain.User;
import todolist.today.today.domain.user.exception.UserNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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

    public void changeTodolistSubject(String userId, String subjectId, TodolistSubjectChangeRequest request) {
        todolistSubjectRepository.findById(UUID.fromString(subjectId))
                .filter(t -> t.getTodolist().getUser().getEmail().equals(userId))
                .orElseThrow(() -> new TodolistSubjectNotFoundException(subjectId))
                .updateSubject(request.getSubject());
    }

    public void changeTemplateSubjectOrder(String userId, String subjectId, TodolistSubjectOrderRequest request) {
        UUID subjectIdUUID = UUID.fromString(subjectId);

        TodolistSubject subject = todolistSubjectRepository.findById(subjectIdUUID)
                .filter(s -> s.getTodolist().getUser().getEmail().equals(userId))
                .orElseThrow(() -> new TemplateSubjectNotFoundException(subjectId));

        int order = request.getOrder();

        List<Integer> values;
        try {
            values = customTodolistSubjectRepository.getTemplateSubjectValueByOrder(subject.getTodolist().getTodolistId(), subjectIdUUID, request.getOrder());
        } catch (IndexOutOfBoundsException e) {
            throw new TemplateSubjectOrderException(order);
        }

        int value1 = values.get(0);
        int value2 = values.get(1);

        subject.updateValue((value1 + value2)/2);
        if (value2 >= 2147483500 || value2 - value1 <= 25) todolistSortService.sortTodolistSubject(subject.getTodolist());
    }
}
