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
                                user.email,
                                user.nickname,
                                user.profile,
                                new CaseBuilder()
                                        .when(user.email.eq(myId))
                                        .then(2)
                                        .when(friend1.friend.email.eq(myId))
                                        .then(1)
                                        .otherwise(0).as("status")))
                        .from(user)
                        .rightJoin(user.receiveFriends, friend1)
                        .on((user.email.eq(friend1.friend.email).or(user.email.eq(friend1.user.email)))
                                .and(user.email.ne(userId)))
                        .where(friend1.friend.email.eq(userId).or(friend1.user.email.eq(userId)))
                        .orderBy(friend1.createdAt.desc())
                        .offset(request.getPage())
                        .limit(request.getSize())
                        .fetch();
    }

    public void deleteFriend(String userId, String myId) {
        query.delete(friend1)
                .where(friend1.friend.email.eq(userId).and(friend1.user.email.eq(myId))
                        .or(friend1.friend.email.eq(userId).and(friend1.user.email.eq(myId))))
                .execute();
    }

    public boolean existsFriend(String userId, String myId) {
        return query.selectFrom(friend1)
                .where(friend1.friend.email.eq(userId).and(friend1.user.email.eq(myId))
                        .or(friend1.friend.email.eq(userId).and(friend1.user.email.eq(myId))))
                .fetchFirst() != null;
    }

}
