package todolist.today.today.domain.template.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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

}
