package todolist.today.today.domain.todolist.dao;

import org.springframework.data.repository.CrudRepository;
import todolist.today.today.domain.todolist.domain.TodolistSubject;

import java.util.UUID;

public interface TodolistSubjectRepository extends CrudRepository<TodolistSubject, UUID> {
}
