package todolist.today.today.domain.todolist.dao;

import org.springframework.data.repository.CrudRepository;
import todolist.today.today.domain.todolist.domain.Todolist;

import java.util.UUID;

public interface TodolistRepository extends CrudRepository<Todolist, UUID> {

    void deleteByUserEmail(String email);

}
