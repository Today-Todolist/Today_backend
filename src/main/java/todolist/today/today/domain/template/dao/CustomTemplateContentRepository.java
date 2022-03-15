package todolist.today.today.domain.template.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static todolist.today.today.domain.template.domain.QTemplateTodolistContent.templateTodolistContent;

@Repository
@RequiredArgsConstructor
public class CustomTemplateContentRepository {

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
        List<Integer> values = query.select(templateTodolistContent.value)
                .from(templateTodolistContent)
                .where(templateTodolistContent.templateTodolistContentId.ne(contentId)
                        .and(templateTodolistContent.templateTodolistSubject.templateTodolistSubjectId.eq(subjectId)))
                .orderBy(templateTodolistContent.value.asc())
                .fetch();
        values.add(0, 0);
        values.add(values.get(values.size()-1) + 200);
        return new ArrayList<>(values.subList(order, order + 2));
    }

}
