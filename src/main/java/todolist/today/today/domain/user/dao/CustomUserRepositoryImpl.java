package todolist.today.today.domain.user.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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

}
