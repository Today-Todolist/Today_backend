package todolist.today.today.domain.todolist.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static todolist.today.today.domain.todolist.domain.QTodolistContent.todolistContent;

@Repository
@RequiredArgsConstructor
public class CustomTodolistContentRepository {

    private final JPAQueryFactory query;

    public int getTodolistContentLastValue(UUID subjectId) {
        Integer value = query.select(todolistContent.value)
                .from(todolistContent)
                .where(todolistContent.todolistSubject.todolistSubjectId.eq(subjectId))
                .orderBy(todolistContent.value.desc())
                .fetchFirst();
        return value != null ? value : 0;
    }

    public List<Integer> getTodolistContentValueByOrder(UUID subjectId, UUID contentId, int order) {
        List<Integer> values = query.select(todolistContent.value)
                .from(todolistContent)
                .where(todolistContent.todolistContentId.ne(contentId)
                        .and(todolistContent.todolistSubject.todolistSubjectId.eq(subjectId)))
                .orderBy(todolistContent.value.asc())
                .fetch();
        values.add(0, 0);
        values.add(values.get(values.size()-1) + 200);
        return new ArrayList<>(values.subList(order, order + 2));
    }

}
