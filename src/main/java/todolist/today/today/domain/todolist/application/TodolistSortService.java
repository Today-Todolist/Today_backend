package todolist.today.today.domain.todolist.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.today.today.domain.todolist.dao.TodolistContentRepository;
import todolist.today.today.domain.todolist.dao.TodolistSubjectRepository;
import todolist.today.today.domain.todolist.domain.Todolist;
import todolist.today.today.domain.todolist.domain.TodolistContent;
import todolist.today.today.domain.todolist.domain.TodolistSubject;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TodolistSortService {

    private final TodolistSubjectRepository todolistSubjectRepository;
    private final TodolistContentRepository todolistContentRepository;

    public int sortTodolistSubject(Todolist todolist) {
        List<TodolistSubject> todolistSubjects = todolist.getTodolistSubjects();
        todolistSubjects.sort((a, b) -> a.getValue() < b.getValue() ? 1 : 0);

        int value = 0;
        for (TodolistSubject subject : todolistSubjects) {
            value += 100;
            subject.updateValue(value);
        }
        todolistSubjectRepository.saveAll(todolistSubjects);
        return value;
    }

    public int sortTodolistContent(TodolistSubject todolistSubject) {
        List<TodolistContent> todolistContents = todolistSubject.getTodolistContents();
        todolistContents.sort((a, b) -> a.getValue() < b.getValue() ? 1 : 0);

        int value = 0;
        for (TodolistContent subject : todolistContents) {
            value += 100;
            subject.updateValue(value);
        }
        todolistContentRepository.saveAll(todolistContents);
        return value;
    }

}
