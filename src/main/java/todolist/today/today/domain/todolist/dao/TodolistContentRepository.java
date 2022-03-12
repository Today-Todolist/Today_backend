package todolist.today.today.domain.todolist.dao;

import org.springframework.data.repository.CrudRepository;
import todolist.today.today.domain.todolist.domain.TodolistContent;

import java.util.UUID;

public interface TodolistContentRepository extends CrudRepository<TodolistContent, UUID> {
}
