package todolist.today.today.domain.friend.dao;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import todolist.today.today.domain.friend.dto.response.UserFriendResponse;
import todolist.today.today.global.dto.request.PagingRequest;

import java.util.List;
import java.util.stream.Stream;

import static todolist.today.today.domain.friend.domain.QFriend.friend1;
import static todolist.today.today.domain.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class CustomFriendRepositoryImpl {

    private final JPAQueryFactory query;

    public List<UserFriendResponse> getUserFriends(String userId, String myId, PagingRequest request) {
        List<UserFriendResponse> response1 =
                query.select(Projections.constructor(UserFriendResponse.class,
                                user.email,
                                user.nickname,
                                user.profile,
                                new CaseBuilder()
                                        .when(user.email.eq(myId)).then(2)
                                        .when(user.receiveFriends.any().friend.email.eq(userId)
                                                .or(user.sendFriends.any().friend.email.eq(userId)))
                                        .then(1)
                                        .otherwise(0)
                        ))
                        .from(friend1)
                        .leftJoin(friend1.user, user)
                        .where(friend1.user.email.eq(userId))
                        .fetch();

        List<UserFriendResponse> response2 =
                query.select(Projections.constructor(UserFriendResponse.class,
                                user.email,
                                user.nickname,
                                user.profile,
                                new CaseBuilder()
                                        .when(user.email.eq(myId)).then(2)
                                        .when(user.receiveFriends.any().friend.email.eq(userId)
                                                .or(user.sendFriends.any().friend.email.eq(userId)))
                                        .then(1)
                                        .otherwise(0)
                        ))
                        .from(friend1)
                        .leftJoin(friend1.friend, user)
                        .where(friend1.friend.email.eq(userId))
                        .fetch();

        return Stream.concat(response1.stream(), response2.stream()).toList();
    }

}
