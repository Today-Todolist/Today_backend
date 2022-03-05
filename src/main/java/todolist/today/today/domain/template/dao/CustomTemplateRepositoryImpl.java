package todolist.today.today.domain.template.dao;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import todolist.today.today.domain.search.dto.response.TemplateSearchResponse;
import todolist.today.today.domain.search.dto.response.template.TemplateSearchTemplateResponse;
import todolist.today.today.domain.search.dto.response.user.TemplateSearchUserResponse;
import todolist.today.today.domain.template.dto.response.RandomTemplateResponse;
import todolist.today.today.domain.template.dto.response.template.RandomTemplateTemplateResponse;
import todolist.today.today.domain.template.dto.response.user.RandomTemplateUserResponse;
import todolist.today.today.global.dto.request.PagingRequest;

import java.util.List;
import java.util.Random;

import static todolist.today.today.domain.friend.domain.QFriend.friend1;
import static todolist.today.today.domain.template.domain.QTemplate.template;
import static todolist.today.today.domain.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class CustomTemplateRepositoryImpl {

    private final JPAQueryFactory query;
    private final Random random = new Random();

    public List<TemplateSearchResponse> getTemplateSearchResult(String word, PagingRequest request) {
        return query.select(Projections.constructor(TemplateSearchResponse.class,
                        Projections.constructor(TemplateSearchUserResponse.class,
                                user.email,
                                user.nickname,
                                user.profile),
                        Projections.constructor(TemplateSearchTemplateResponse.class,
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
        return query.select(Projections.constructor(RandomTemplateResponse.class,
                        Projections.constructor(RandomTemplateUserResponse.class,
                                user.email,
                                user.nickname,
                                user.profile),
                        Projections.constructor(RandomTemplateTemplateResponse.class,
                                template.templateId,
                                template.title,
                                template.profile)))
                .from(template)
                .leftJoin(template.user, user)
                .offset(random.nextInt((int) (count/size)))
                .limit(size)
                .fetch();
    }

}
