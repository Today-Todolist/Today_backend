package todolist.today.today.domain.todolist.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.today.today.domain.check.application.CheckService;
import todolist.today.today.domain.todolist.dao.CustomTodolistContentRepositoryImpl;
import todolist.today.today.domain.todolist.dao.TodolistContentRepository;
import todolist.today.today.domain.todolist.dao.TodolistSubjectRepository;
import todolist.today.today.domain.todolist.domain.TodolistContent;
import todolist.today.today.domain.todolist.domain.TodolistSubject;
import todolist.today.today.domain.todolist.dto.request.TodolistContentChangeRequest;
import todolist.today.today.domain.todolist.dto.request.TodolistContentCreateRequest;
import todolist.today.today.domain.todolist.dto.request.TodolistContentOrderRequest;
import todolist.today.today.domain.todolist.exception.TodolistContentNotFoundException;
import todolist.today.today.domain.todolist.exception.TodolistContentOrderException;
import todolist.today.today.domain.todolist.exception.TodolistSubjectNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TodolistContentService {

    private final TodolistSubjectRepository todolistSubjectRepository;
    private final CustomTodolistContentRepositoryImpl customTodolistContentRepository;
    private final TodolistContentRepository todolistContentRepository;
    private final TodolistSortService todolistSortService;
    private final CheckService checkService;

    public void makeTodolistSubject(String userId, TodolistContentCreateRequest request) {
        String subjectId = request.getId();
        UUID subjectIdUUID = UUID.fromString(subjectId);

        TodolistSubject subject = todolistSubjectRepository.findById(subjectIdUUID)
                .filter(s -> s.getTodolist().getUser().getEmail().equals(userId))
                .orElseThrow(() -> new TodolistSubjectNotFoundException(subjectId));

        int value = customTodolistContentRepository.getTodolistContentLastValue(subjectIdUUID);
        if (value >= 2147483500) value = todolistSortService.sortTodolistContent(subject);
        TodolistContent content = TodolistContent.builder()
                .todolistSubject(subject)
                .content(request.getContent())
                .value(value + 100)
                .build();
        todolistContentRepository.save(content);
    }

    public void changeTodolistContent(String userId, String contentId, TodolistContentChangeRequest request) {
        checkService.checkEditAvailability(userId);
        todolistContentRepository.findById(UUID.fromString(contentId))
                .filter(c -> c.getTodolistSubject().getTodolist().getUser().getEmail().equals(userId))
                .orElseThrow(() -> new TodolistContentNotFoundException(contentId))
                .updateContent(request.getContent());
    }

    public void changeTodolistContentOrder(String userId, String contentId, TodolistContentOrderRequest request) {
        checkService.checkEditAvailability(userId);
        UUID contentIdUUID = UUID.fromString(contentId);

        TodolistContent content = todolistContentRepository.findById(UUID.fromString(contentId))
                .filter(c -> c.getTodolistSubject().getTodolist().getUser().getEmail().equals(userId))
                .orElseThrow(() -> new TodolistContentNotFoundException(contentId));

        int order = request.getOrder();

        List<Integer> values;
        try {
            values = customTodolistContentRepository
                    .getTodolistContentValueByOrder(content.getTodolistSubject().getTodolistSubjectId(), contentIdUUID, order);
        } catch (IndexOutOfBoundsException e) {
            throw new TodolistContentOrderException(order);
        }

        int value1 = values.get(0);
        int value2 = values.get(1);

        content.updateValue((value1 + value2)/2);
        if (value2 >= 2147483500 || value2 - value1 <= 25) todolistSortService.sortTodolistContent(content.getTodolistSubject());
    }

    public void deleteTodolistContent(String userId, String contentId) {
        TodolistContent content = todolistContentRepository.findById(UUID.fromString(contentId))
                .filter(c -> c.getTodolistSubject().getTodolist().getUser().getEmail().equals(userId))
                .orElseThrow(() -> new TodolistContentNotFoundException(contentId));
        todolistContentRepository.delete(content);
    }

}
