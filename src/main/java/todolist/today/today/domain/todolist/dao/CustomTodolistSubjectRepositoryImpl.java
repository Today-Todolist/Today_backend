package todolist.today.today.domain.todolist.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static todolist.today.today.domain.template.domain.QTemplateTodolistSubject.templateTodolistSubject;
import static todolist.today.today.domain.todolist.domain.QTodolistSubject.todolistSubject;

@Repository
@RequiredArgsConstructor
public class CustomTodolistSubjectRepositoryImpl {

    private final JPAQueryFactory query;

    public int getTodolistSubjectLastValue(UUID todolistId) {
        Integer value = query.select(templateTodolistSubject.value)
                .from(todolistSubject)
                .where(todolistSubject.todolist.todolistId.eq(todolistId))
                .orderBy(todolistSubject.value.desc())
                .fetchFirst();
        return value != null ? value : 0;
    }

}
