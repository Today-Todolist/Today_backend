package todolist.today.today.domain.user.dao;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import todolist.today.today.domain.user.dto.response.MyInfoResponse;
import todolist.today.today.domain.user.dto.response.template.MyInfoTemplateResponse;

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

    public MyInfoResponse getMyInfo(String userId) {
        return query.select(Projections.constructor(MyInfoResponse.class,
                        user.email,
                        user.nickname,
                        user.profile,
                        friend1.count(),
                        JPAExpressions.select(Projections.constructor(MyInfoTemplateResponse.class,
                                        template.templateId,
                                        template.title,
                                        template.profile))
                                .from(template)
                                .where(template.user.email.eq(userId))
                        ))
                .from(user)
                .join(friend1)
                .where(user.email.eq(userId))
                .on(friend1.user.email.eq(userId).or(friend1.friend.email.eq(userId)))
                .fetchOne();
    }

}
