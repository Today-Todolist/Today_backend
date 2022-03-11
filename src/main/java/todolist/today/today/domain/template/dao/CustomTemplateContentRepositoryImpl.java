package todolist.today.today.domain.template.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static todolist.today.today.domain.template.domain.QTemplateTodolistContent.templateTodolistContent;

@Repository
@RequiredArgsConstructor
public class CustomTemplateContentRepositoryImpl {

    private final JPAQueryFactory query;

    public int getTemplateContentLastValue(UUID subjectId) {
        Integer value = query.select(templateTodolistContent.value)
                .from(templateTodolistContent)
                .where(templateTodolistContent.templateTodolistSubject.templateTodolistSubjectId.eq(subjectId))
                .orderBy(templateTodolistContent.value.desc())
                .fetchFirst();
        return value != null ? value : 0;
    }

    public List<Integer> getTemplateContentValueByOrder(UUID subjectId, UUID contentId, int order) {
        return query.select(templateTodolistContent.value)
                .from(templateTodolistContent)
                .where(templateTodolistContent.templateTodolistContentId.ne(contentId)
                        .and(templateTodolistContent.templateTodolistSubject.templateTodolistSubjectId.eq(subjectId)))
                .orderBy(templateTodolistContent.value.asc())
                .fetch().subList(Math.max(order - 1, 0), order + 1);
    }

}
