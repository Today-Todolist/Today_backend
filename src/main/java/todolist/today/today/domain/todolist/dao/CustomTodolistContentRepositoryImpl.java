package todolist.today.today.domain.todolist.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static todolist.today.today.domain.todolist.domain.QTodolistContent.todolistContent;

@Repository
@RequiredArgsConstructor
public class CustomTodolistContentRepositoryImpl {

    private final JPAQueryFactory query;

    public int getTodolistContentLastValue(UUID subjectId) {
        Integer value = query.select(todolistContent.value)
                .from(todolistContent)
                .where(todolistContent.todolistSubject.todolistSubjectId.eq(subjectId))
                .orderBy(todolistContent.value.desc())
                .fetchFirst();
        return value != null ? value : 0;
    }

}
