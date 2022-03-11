package todolist.today.today.domain.todolist.dao;

import org.springframework.data.repository.CrudRepository;
import todolist.today.today.domain.todolist.domain.Todolist;
import todolist.today.today.domain.user.domain.User;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface TodolistRepository extends CrudRepository<Todolist, UUID> {

    void deleteByUserEmail(String email);
    long countByUserEmail(String email);
    Optional<Todolist> findByUserAndDate(User user, LocalDate date);

}
