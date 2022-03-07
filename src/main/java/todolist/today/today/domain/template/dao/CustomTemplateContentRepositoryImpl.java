package todolist.today.today.domain.template.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static todolist.today.today.domain.template.domain.QTemplateTodolistContent.templateTodolistContent;

@Repository
@RequiredArgsConstructor
public class CustomTemplateContentRepositoryImpl {

    private final JPAQueryFactory query;

    public int getTemplateContentLastValue(String subjectId) {
        return query.select(templateTodolistContent.value)
                .from(templateTodolistContent)
                .where(templateTodolistContent.templateTodolistSubject.templateTodolistSubjectId.eq(UUID.fromString(subjectId)))
                .orderBy(templateTodolistContent.value.desc())
                .fetchFirst();
    }

}
