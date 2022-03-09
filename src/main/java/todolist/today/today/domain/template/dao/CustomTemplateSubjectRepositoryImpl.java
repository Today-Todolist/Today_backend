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
        Integer value = query.select(templateTodolistSubject.value)
                .from(templateTodolistSubject)
                .where(templateTodolistSubject.templateDay.templateDayId.eq(templateDayId))
                .orderBy(templateTodolistSubject.value.desc())
                .fetchFirst();
        return value != null ? value : 0;
    }

    public List<Integer> getTemplateSubjectValueByOrder(UUID templateDayId, String subjectId, int order) {
        return query.select(templateTodolistSubject.value)
                .from(templateTodolistSubject)
                .where(templateTodolistSubject.templateTodolistSubjectId.ne(UUID.fromString(subjectId))
                        .and(templateTodolistSubject.templateDay.templateDayId.eq(templateDayId)))
                .orderBy(templateTodolistSubject.value.asc())
                .fetch().subList(Math.max((order - 1), 0), order + 1);
    }

}
