package todolist.today.today.domain.template.dao;

import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import todolist.today.today.domain.search.dto.response.TemplateSearchResponse;
import todolist.today.today.domain.search.dto.response.template.TemplateSearchTemplateResponse;
import todolist.today.today.domain.search.dto.response.user.TemplateSearchUserResponse;
import todolist.today.today.domain.template.dto.response.MyTemplateResponse;
import todolist.today.today.domain.template.dto.response.RandomTemplateResponse;
import todolist.today.today.domain.template.dto.response.TemplateContentResponse;
import todolist.today.today.domain.template.dto.response.template.RandomTemplateTemplateResponse;
import todolist.today.today.domain.template.dto.response.template.TemplateContentTemplateResponse;
import todolist.today.today.domain.template.dto.response.template.content.TemplateContentTemplateContentResponse;
import todolist.today.today.domain.template.dto.response.template.subject.TemplateContentTemplateSubjectResponse;
import todolist.today.today.domain.template.dto.response.user.RandomTemplateUserResponse;
import todolist.today.today.domain.todolist.dto.etc.TemplateContentDto;
import todolist.today.today.domain.todolist.dto.etc.content.TemplateContentSubjectContentDto;
import todolist.today.today.domain.todolist.dto.etc.subject.TemplateContentSubjectDto;
import todolist.today.today.global.dto.request.PagingRequest;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.querydsl.core.types.Projections.constructor;
import static com.querydsl.core.types.Projections.list;
import static todolist.today.today.domain.friend.domain.QFriend.friend1;
import static todolist.today.today.domain.template.domain.QTemplate.template;
import static todolist.today.today.domain.template.domain.QTemplateDay.templateDay;
import static todolist.today.today.domain.template.domain.QTemplateTodolistContent.templateTodolistContent;
import static todolist.today.today.domain.template.domain.QTemplateTodolistSubject.templateTodolistSubject;
import static todolist.today.today.domain.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class CustomTemplateRepository {

    private final JPAQueryFactory query;
    private final Random random = new Random();

    public List<TemplateSearchResponse> getTemplateSearchResult(String word, PagingRequest request) {
        return query.select(constructor(TemplateSearchResponse.class,
                        constructor(TemplateSearchUserResponse.class,
                                user.email,
                                user.nickname,
                                user.profile),
                        constructor(TemplateSearchTemplateResponse.class,
                                template.templateId,
                                template.title,
                                template.profile)))
                .from(template)
                .leftJoin(template.user, user)
                .leftJoin(friend1).on(friend1.user.email.eq(user.email)
                        .or(friend1.friend.email.eq(user.email)))
                .where(template.title.contains(word))
                .orderBy(friend1.count().desc())
                .offset(request.getPage())
                .limit(request.getSize())
                .fetch();
    }

    public List<RandomTemplateResponse> getRandomTemplate(int size, long count) {
        return query.select(constructor(RandomTemplateResponse.class,
                        constructor(RandomTemplateUserResponse.class,
                                user.email,
                                user.nickname,
                                user.profile),
                        constructor(RandomTemplateTemplateResponse.class,
                                template.templateId,
                                template.title,
                                template.profile)))
                .from(template)
                .leftJoin(template.user, user)
                .offset(random.nextInt(((int) Math.ceil((double) count/size))))
                .limit(size)
                .fetch();
    }

    public List<MyTemplateResponse> getMyTemplate(String userId) {
        return query.select(constructor(MyTemplateResponse.class,
                        template.templateId,
                        template.title,
                        template.profile))
                .from(template)
                .where(template.user.email.eq(userId))
                .fetch();
    }

    public TemplateContentResponse getTemplateContent(String userId, UUID templateId, int day) {
        return query.select(constructor(TemplateContentResponse.class,
                        template.title,
                        template.profile,
                        template.size,
                        new CaseBuilder()
                                .when(template.user.email.eq(userId)).then(1)
                                .otherwise(0),
                        list(constructor(TemplateContentTemplateResponse.class,
                                constructor(TemplateContentTemplateSubjectResponse.class,
                                    templateTodolistSubject.templateTodolistSubjectId,
                                    templateTodolistSubject.subject),
                                list(constructor(TemplateContentTemplateContentResponse.class,
                                            templateTodolistContent.templateTodolistContentId,
                                            templateTodolistContent.content))))))
                .from(templateDay)
                .leftJoin(templateDay.template, template)
                .leftJoin(templateDay.templateTodolistSubjects, templateTodolistSubject)
                .leftJoin(templateTodolistSubject.templateTodolistContents, templateTodolistContent)
                .where(templateDay.day.eq(day).and(template.templateId.eq(templateId)))
                .orderBy(templateTodolistSubject.value.asc(), templateTodolistContent.value.asc())
                .fetchOne();
    }

    public String getTemplateProfile(String userId, UUID templateId) {
        return query.select(template.profile)
                .from(template)
                .where(template.templateId.eq(templateId).and(template.user.email.eq(userId)))
                .fetchOne();
    }

    public List<TemplateContentDto> getUserTemplateInfo(String userId, UUID templateId) {
        return query.select(constructor(TemplateContentDto.class,
                        templateDay.day,
                        list(constructor(TemplateContentSubjectDto.class,
                                templateTodolistSubject.subject,
                                list(constructor(TemplateContentSubjectContentDto.class,
                                        templateTodolistContent.content))))))
                .from(templateDay)
                .leftJoin(templateDay.templateTodolistSubjects, templateTodolistSubject)
                .leftJoin(templateTodolistSubject.templateTodolistContents, templateTodolistContent)
                .where(templateDay.template.user.email.eq(userId).and(templateDay.template.templateId.eq(templateId)))
                .orderBy(templateDay.day.asc(), templateTodolistSubject.value.asc(), templateTodolistContent.value.asc())
                .fetch();
    }

}
