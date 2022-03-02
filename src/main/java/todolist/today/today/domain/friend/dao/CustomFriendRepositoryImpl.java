package todolist.today.today.domain.friend.dao;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import todolist.today.today.domain.friend.dto.response.UserFriendResponse;
import todolist.today.today.global.dto.request.PagingRequest;

import java.util.List;

import static todolist.today.today.domain.friend.domain.QFriend.friend1;
import static todolist.today.today.domain.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class CustomFriendRepositoryImpl {

    private final JPAQueryFactory query;

    public List<UserFriendResponse> getUserFriends(String userId, String myId, PagingRequest request) {
        return query.select(Projections.constructor(UserFriendResponse.class,
                                new CaseBuilder()
                                        .when(friend1.friend.email.eq(userId)).then(friend1.user.email)
                                        .otherwise(friend1.friend.email),
                                new CaseBuilder()
                                        .when(friend1.friend.email.eq(userId)).then(friend1.user.nickname)
                                        .otherwise(friend1.friend.nickname),
                                new CaseBuilder()
                                        .when(friend1.friend.email.eq(userId)).then(friend1.user.profile)
                                        .otherwise(friend1.friend.profile),
                                new CaseBuilder()
                                        .when(user.email.eq(myId)).then(2)
                                        .when(user.receiveFriends.any().friend.email.eq(userId)
                                                .or(user.sendFriends.any().friend.email.eq(userId)))
                                        .then(1)
                                        .otherwise(0)
                        ))
                        .from(friend1)
                        .where(friend1.friend.email.eq(userId).or(friend1.user.email.eq(userId)))
                        .orderBy(friend1.createdAt.desc())
                        .offset(request.getPage())
                        .limit(request.getSize())
                        .fetch();
    }

}
