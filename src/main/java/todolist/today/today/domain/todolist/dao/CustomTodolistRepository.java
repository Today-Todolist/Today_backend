package todolist.today.today.domain.todolist.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import todolist.today.today.domain.todolist.dto.response.TodolistContentResponse;
import todolist.today.today.domain.todolist.dto.response.TodolistRecordResponse;
import todolist.today.today.domain.todolist.dto.response.todolist.MyCalendarFutureResponse;
import todolist.today.today.domain.todolist.dto.response.todolist.MyCalendarPastResponse;
import todolist.today.today.domain.todolist.dto.response.todolist.UserCalendarFutureResponse;
import todolist.today.today.domain.todolist.dto.response.todolist.UserCalendarPastResponse;
import todolist.today.today.domain.todolist.dto.response.todolist.content.TodolistContentTodolistContentResponse;
import todolist.today.today.domain.todolist.dto.response.todolist.subject.TodolistContentTodolistSubjectResponse;

import java.time.LocalDate;
import java.util.List;

import static com.querydsl.core.types.Projections.constructor;
import static com.querydsl.core.types.Projections.list;
import static todolist.today.today.domain.todolist.domain.QTodolist.todolist;
import static todolist.today.today.domain.todolist.domain.QTodolistContent.todolistContent;
import static todolist.today.today.domain.todolist.domain.QTodolistSubject.todolistSubject;

@Repository
@RequiredArgsConstructor
public class CustomTodolistRepository {

    private final JPAQueryFactory query;

    public List<TodolistRecordResponse> getTodolistRecord(String userId, LocalDate startDate, LocalDate endDate) {
        return query.select(constructor(TodolistRecordResponse.class,
                        todolist.date,
                        list(todolistContent.isSuccess)))
                .from(todolist)
                .leftJoin(todolist.todolistSubjects, todolistSubject)
                .leftJoin(todolistSubject.todolistContents, todolistContent)
                .where(todolist.user.email.eq(userId).and(todolist.date.after(startDate)).and(todolist.date.before(endDate)))
                .orderBy(todolist.date.asc())
                .fetch();
    }

    public List<MyCalendarPastResponse> getMyCalendarPast(String userId, LocalDate startDate) {
        return query.select(constructor(MyCalendarPastResponse.class,
                        todolist.date.dayOfMonth(),
                        list(todolistContent.isSuccess)))
                .from(todolist)
                .leftJoin(todolist.todolistSubjects, todolistSubject)
                .leftJoin(todolistSubject.todolistContents, todolistContent)
                .where(todolist.user.email.eq(userId).and(todolist.date.after(startDate))
                        .and(todolist.date.before(LocalDate.now().plusDays(1))))
                .orderBy(todolist.date.asc())
                .fetch().stream().filter(response -> response.getIsSuccess() != null).toList();
    }

    public List<MyCalendarFutureResponse> getMyCalendarFuture(String userId, LocalDate endDate) {
        return query.select(constructor(MyCalendarFutureResponse.class,
                        todolist.date.dayOfMonth(),
                        todolistSubject.todolistContents.size()))
                .from(todolist)
                .leftJoin(todolist.todolistSubjects, todolistSubject)
                .where(todolist.user.email.eq(userId).and(todolist.date.after(LocalDate.now()))
                        .and(todolist.date.before(endDate)))
                .orderBy(todolist.date.asc())
                .fetch().stream().filter(response -> response.getTodolists() > 0).toList();
    }

    public List<UserCalendarPastResponse> getUserCalendarPast(String userId, LocalDate startDate) {
        return query.select(constructor(UserCalendarPastResponse.class,
                        todolist.date.dayOfMonth(),
                        list(todolistContent.isSuccess)))
                .from(todolist)
                .leftJoin(todolist.todolistSubjects, todolistSubject)
                .leftJoin(todolistSubject.todolistContents, todolistContent)
                .where(todolist.user.email.eq(userId).and(todolist.date.after(startDate))
                        .and(todolist.date.before(LocalDate.now().plusDays(1))))
                .orderBy(todolist.date.asc())
                .fetch().stream().filter(response -> response.getIsSuccess() != null).toList();
    }

    public List<UserCalendarFutureResponse> getUserCalendarFuture(String userId, LocalDate endDate) {
        return query.select(constructor(UserCalendarFutureResponse.class,
                        todolist.date.dayOfMonth(),
                        todolistSubject.todolistContents.size()))
                .from(todolist)
                .leftJoin(todolist.todolistSubjects, todolistSubject)
                .where(todolist.user.email.eq(userId).and(todolist.date.after(LocalDate.now()))
                        .and(todolist.date.before(endDate)))
                .orderBy(todolist.date.asc())
                .fetch().stream().filter(response -> response.getTodolists() > 0).toList();
    }

    public List<TodolistContentResponse> getTodolist(String userId, LocalDate date) {
        return query.select(constructor(TodolistContentResponse.class,
                        constructor(TodolistContentTodolistSubjectResponse.class,
                                todolistSubject.todolistSubjectId,
                                todolistSubject.subject,
                                list(todolistContent.isSuccess)),
                        list(constructor(TodolistContentTodolistContentResponse.class,
                                todolistContent.todolistContentId,
                                todolistContent.content,
                                todolistContent.isSuccess))))
                .from(todolist)
                .leftJoin(todolist.todolistSubjects, todolistSubject)
                .leftJoin(todolistSubject.todolistContents, todolistContent)
                .where(todolist.user.email.eq(userId).and(todolist.date.eq(date)))
                .orderBy(todolistSubject.value.asc(), todolistContent.value.asc())
                .fetch();
    }

}
