package todolist.today.today.domain.friend.dao;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import todolist.today.today.domain.friend.dto.response.UserFriendApplyResponse;
import todolist.today.today.global.dto.request.PagingRequest;

import java.util.List;

import static todolist.today.today.domain.friend.domain.QFriendApply.friendApply;
import static todolist.today.today.domain.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class CustomFriendApplyRepositoryImpl {

    private final JPAQueryFactory query;

    public List<UserFriendApplyResponse> getUserFriendApply(String userId, PagingRequest request) {
        return query.select(Projections.constructor(UserFriendApplyResponse.class,
                        user.email,
                        user.nickname,
                        user.profile))
                .from(friendApply)
                .leftJoin(friendApply.user, user)
                .where(friendApply.friend.email.eq(userId))
                .orderBy(friendApply.createdAt.desc())
                .offset(request.getPage())
                .limit(request.getSize())
                .fetch();
    }

}
