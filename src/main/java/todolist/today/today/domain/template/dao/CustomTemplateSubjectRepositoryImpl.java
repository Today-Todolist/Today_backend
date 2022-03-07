package todolist.today.today.domain.template.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static todolist.today.today.domain.template.domain.QTemplateTodolistSubject.templateTodolistSubject;

@Repository
@RequiredArgsConstructor
public class CustomTemplateSubjectRepositoryImpl {

    private final JPAQueryFactory query;

    public int getTemplateSubjectLastValue(UUID templateDayId) {
        return query.select(templateTodolistSubject.value)
                .from(templateTodolistSubject)
                .where(templateTodolistSubject.templateDay.templateDayId.eq(templateDayId))
                .orderBy(templateTodolistSubject.value.desc())
                .fetchFirst();
    }

    public List<Integer> getTemplateSubjectValueByOrder(String subjectId, int order) {
        return query.select(templateTodolistSubject.value)
                .from(templateTodolistSubject)
                .where(templateTodolistSubject.templateTodolistSubjectId.eq(UUID.fromString(subjectId)))
                .orderBy(templateTodolistSubject.value.asc())
                .fetch().subList(order - 1, order);
    }

}
