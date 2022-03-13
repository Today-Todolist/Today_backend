package todolist.today.today.domain.todolist.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.today.today.domain.todolist.dao.TodolistContentRepository;
import todolist.today.today.domain.todolist.exception.TodolistContentNotFoundException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TodolistClearService {

    private final TodolistContentRepository todolistContentRepository;

    public void changeIsSuccess(String userId, String contentId, boolean isSuccess) {
        todolistContentRepository.findById(UUID.fromString(contentId))
                .filter(c -> c.getTodolistSubject().getTodolist().getUser().getEmail().equals(userId))
                .orElseThrow(() -> new TodolistContentNotFoundException(contentId))
                .updateIsSuccess(isSuccess);
    }

}
