package todolist.today.today.domain.todolist.dao;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import todolist.today.today.domain.todolist.dto.TodolistRecordResponse;

import java.time.LocalDate;
import java.util.List;

import static todolist.today.today.domain.todolist.domain.QTodolist.todolist;
import static todolist.today.today.domain.todolist.domain.QTodolistContent.todolistContent;
import static todolist.today.today.domain.todolist.domain.QTodolistSubject.todolistSubject;

@Repository
@RequiredArgsConstructor
public class CustomTodolistRepositoryImpl {

    private final JPAQueryFactory query;

    public List<TodolistRecordResponse> getTodolistRecord(String userId, LocalDate startDate, LocalDate endDate) {
        return query.select(Projections.constructor(TodolistRecordResponse.class,
                        todolist.date,
                        JPAExpressions.select(todolistContent.count())
                                .from(todolistContent)
                                .where(todolistContent.isSuccess.eq(true)),
                        JPAExpressions.select(todolistContent.count())
                                .from(todolistContent)
                                .where(todolistContent.isSuccess.eq(false))))
                .from(todolist)
                .leftJoin(todolist.todolistSubjects, todolistSubject)
                .leftJoin(todolistSubject.todolistContents, todolistContent)
                .where(todolist.user.email.eq(userId).and(todolist.date.after(startDate)).and(todolist.date.before(endDate)))
                .fetch();
    }

}
