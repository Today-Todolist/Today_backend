package todolist.today.today.domain.todolist.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
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

    public List<Integer> getTemplateSubjectValueByOrder(UUID todolistId, UUID subjectId, int order) {
        List<Integer> values = query.select(todolistSubject.value)
                .from(todolistSubject)
                .where(todolistSubject.todolistSubjectId.ne(subjectId)
                        .and(todolistSubject.todolist.todolistId.eq(todolistId)))
                .orderBy(todolistSubject.value.asc())
                .fetch();
        values.add(0, 0);
        values.add(values.get(values.size()-1) + 200);
        return new ArrayList<>(values.subList(order, order + 2));
    }
}
