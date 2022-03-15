package todolist.today.today.domain.template.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static todolist.today.today.domain.template.domain.QTemplateTodolistSubject.templateTodolistSubject;

@Repository
@RequiredArgsConstructor
public class CustomTemplateSubjectRepository {

    private final JPAQueryFactory query;

    public int getTemplateSubjectLastValue(UUID templateDayId) {
        Integer value = query.select(templateTodolistSubject.value)
                .from(templateTodolistSubject)
                .where(templateTodolistSubject.templateDay.templateDayId.eq(templateDayId))
                .orderBy(templateTodolistSubject.value.desc())
                .fetchFirst();
        return value != null ? value : 0;
    }

    public List<Integer> getTemplateSubjectValueByOrder(UUID templateDayId, UUID subjectId, int order) {
        List<Integer> values = query.select(templateTodolistSubject.value)
                .from(templateTodolistSubject)
                .where(templateTodolistSubject.templateTodolistSubjectId.ne(subjectId)
                        .and(templateTodolistSubject.templateDay.templateDayId.eq(templateDayId)))
                .orderBy(templateTodolistSubject.value.asc())
                .fetch();
        values.add(0, 0);
        values.add(values.get(values.size()-1) + 200);
        return new ArrayList<>(values.subList(order, order + 2));
    }

}
