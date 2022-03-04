package todolist.today.today.domain.user.dao;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import todolist.today.today.domain.search.dto.response.NicknameSearchResponse;
import todolist.today.today.domain.user.dto.response.MyInfoResponse;
import todolist.today.today.domain.user.dto.response.UserInfoResponse;
import todolist.today.today.domain.user.dto.response.template.MyInfoTemplateResponse;
import todolist.today.today.domain.user.dto.response.template.UserInfoTemplateResponse;
import todolist.today.today.global.dto.request.PagingRequest;

import java.util.List;

import static todolist.today.today.domain.friend.domain.QFriend.friend1;
import static todolist.today.today.domain.template.domain.QTemplate.template;
import static todolist.today.today.domain.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class CustomUserRepositoryImpl {

    private final JPAQueryFactory query;

    public String findPasswordById(String userId) {
        return query.select(user.password)
                .from(user)
                .where(user.email.eq(userId))
                .fetchOne();
    }

    public String findNicknameById(String userId) {
        return query.select(user.nickname)
                .from(user)
                .where(user.email.eq(userId))
                .fetchOne();
    }

    public boolean findChangePossibleById(String userId) {
        return Boolean.TRUE.equals(query.select(user.changePossible)
                .from(user)
                .where(user.email.eq(userId))
                .fetchOne());
    }

    public MyInfoResponse getMyInfo(String userId) {
        List<MyInfoTemplateResponse> templates =
                query.select(Projections.constructor(MyInfoTemplateResponse.class,
                                template.templateId,
                                template.title,
                                template.profile))
                        .from(template)
                        .where(template.user.email.eq(userId))
                        .fetch();

        MyInfoResponse response =
                query.select(Projections.constructor(MyInfoResponse.class,
                                user.email,
                                user.nickname,
                                user.profile,
                                friend1.count()))
                        .from(user)
                        .leftJoin(friend1).on(friend1.user.email.eq(userId).or(friend1.friend.email.eq(userId)))
                        .where(user.email.eq(userId))
                        .fetchOne();

        response.setTemplates(templates);
        return response;
    }

    public UserInfoResponse getUserInfo(String userId, String myId) {
        List<UserInfoTemplateResponse> templates =
                query.select(Projections.constructor(UserInfoTemplateResponse.class,
                                template.templateId,
                                template.title,
                                template.profile))
                        .from(template)
                        .where(template.user.email.eq(userId))
                        .fetch();

        UserInfoResponse response =
                query.select(Projections.constructor(UserInfoResponse.class,
                                user.nickname,
                                user.profile,
                                friend1.count(),
                                new CaseBuilder()
                                        .when(user.email.eq(myId)).then(2)
                                        .when(friend1.friend.email.eq(myId).or(friend1.user.email.eq(myId))).then(1)
                                        .otherwise(0)))
                        .from(user)
                        .leftJoin(friend1).on(friend1.user.email.eq(userId).or(friend1.friend.email.eq(userId)))
                        .where(user.email.eq(userId))
                        .fetchOne();

        response.setTemplates(templates);
        return response;
    }

    public List<NicknameSearchResponse> getNicknameSearchResult(String userId, String word, PagingRequest request) {
        return query.select(Projections.constructor(NicknameSearchResponse.class,
                        user.email,
                        user.nickname,
                        user.profile,
                        new CaseBuilder()
                                .when(user.email.eq(userId)).then(2)
                                .when(friend1.friend.email.eq(userId).or(friend1.user.email.eq(userId))).then(1)
                                .otherwise(0)))
                .from(user)
                .leftJoin(friend1).on(friend1.user.email.eq(userId).or(friend1.friend.email.eq(userId)))
                .where(user.nickname.contains(word))
                .orderBy(friend1.count().desc())
                .fetch();
    }

}
