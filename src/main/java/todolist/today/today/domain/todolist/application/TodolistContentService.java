package todolist.today.today.domain.todolist.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.today.today.domain.todolist.dao.CustomTodolistContentRepositoryImpl;
import todolist.today.today.domain.todolist.dao.TodolistContentRepository;
import todolist.today.today.domain.todolist.dao.TodolistSubjectRepository;
import todolist.today.today.domain.todolist.domain.TodolistContent;
import todolist.today.today.domain.todolist.domain.TodolistSubject;
import todolist.today.today.domain.todolist.dto.request.TodolistContentCreateRequest;
import todolist.today.today.domain.todolist.exception.TodolistSubjectNotFoundException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TodolistContentService {

    private final TodolistSubjectRepository todolistSubjectRepository;
    private final CustomTodolistContentRepositoryImpl customTodolistContentRepository;
    private final TodolistContentRepository todolistContentRepository;
    private final TodolistSortService todolistSortService;

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

}
