package todolist.today.today.domain.todolist.dao;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import todolist.today.today.domain.todolist.dto.response.TodolistContentResponse;
import todolist.today.today.domain.todolist.dto.response.TodolistRecordResponse;
import todolist.today.today.domain.todolist.dto.response.todolist.MyCalendarFutureResponse;
import todolist.today.today.domain.todolist.dto.response.todolist.MyCalendarPastResponse;
import todolist.today.today.domain.todolist.dto.response.todolist.UserCalendarFutureResponse;
import todolist.today.today.domain.todolist.dto.response.todolist.UserCalendarPastResponse;
import todolist.today.today.domain.todolist.dto.response.todolist.subject.TodolistContentTodolistSubjectResponse;

import java.time.LocalDate;
import java.util.List;

import static com.querydsl.core.types.Projections.list;
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

    public List<MyCalendarPastResponse> getMyCalendarPast(String userId, LocalDate startDate) {
        return query.select(Projections.constructor(MyCalendarPastResponse.class,
                        todolist.date.month(),
                        new CaseBuilder()
                                .when(todolist.todolistSubjects.any().todolistContents.any().isSuccess.eq(true)).then(true)
                                .otherwise(false)))
                .from(todolist)
                .where(todolist.user.email.eq(userId).and(todolist.date.after(startDate))
                        .and(todolist.date.before(LocalDate.now())))
                .fetch();
    }

    public List<MyCalendarFutureResponse> getMyCalendarFuture(String userId, LocalDate endDate) {
        return query.select(Projections.constructor(MyCalendarFutureResponse.class,
                        todolist.date.month(),
                        todolist.todolistSubjects.any().todolistContents.size()))
                .from(todolist)
                .where(todolist.user.email.eq(userId).and(todolist.date.after(LocalDate.now().plusDays(1)))
                        .and(todolist.date.before(endDate)))
                .fetch();
    }

    public List<UserCalendarPastResponse> getUserCalendarPast(String userId, LocalDate startDate) {
        return query.select(Projections.constructor(UserCalendarPastResponse.class,
                        todolist.date.month(),
                        new CaseBuilder()
                                .when(todolist.todolistSubjects.any().todolistContents.any().isSuccess.eq(true)).then(true)
                                .otherwise(false)))
                .from(todolist)
                .where(todolist.user.email.eq(userId).and(todolist.date.after(startDate))
                        .and(todolist.date.before(LocalDate.now())))
                .fetch();
    }

    public List<UserCalendarFutureResponse> getUserCalendarFuture(String userId, LocalDate endDate) {
        return query.select(Projections.constructor(UserCalendarFutureResponse.class,
                        todolist.date.month(),
                        todolist.todolistSubjects.any().todolistContents.size()))
                .from(todolist)
                .where(todolist.user.email.eq(userId).and(todolist.date.after(LocalDate.now().plusDays(1)))
                        .and(todolist.date.before(endDate)))
                .fetch();
    }

    public TodolistContentResponse getTodolist(String userId, LocalDate date) {
        return query.select(Projections.constructor(TodolistContentResponse.class,
                        Projections.constructor(TodolistContentTodolistSubjectResponse.class,
                                todolistSubject.todolistSubjectId,
                                todolistSubject.subject,
                                new CaseBuilder()
                                        .when(todolistSubject.todolistContents.any().isSuccess.eq(true)).then(true)
                                        .otherwise(false)),
                        list(Projections.constructor(TodolistContentTodolistSubjectResponse.class,
                                todolistContent.todolistContentId,
                                todolistContent.content,
                                todolistContent.isSuccess))))
                .from(todolist)
                .leftJoin(todolist.todolistSubjects, todolistSubject)
                .leftJoin(todolistSubject.todolistContents, todolistContent)
                .where(todolist.user.email.eq(userId).and(todolist.date.eq(date)))
                .fetchOne();
    }

}
