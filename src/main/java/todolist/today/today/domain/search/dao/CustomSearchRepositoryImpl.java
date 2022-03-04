package todolist.today.today.domain.search.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import todolist.today.today.domain.search.dto.response.SearchAmountResponse;

import java.util.List;

import static todolist.today.today.domain.search.domain.QSearchWord.searchWord1;
import static todolist.today.today.domain.template.domain.QTemplate.template;
import static todolist.today.today.domain.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class CustomSearchRepositoryImpl {

    private final JPAQueryFactory query;

    public List<String> getSearchWord(String word) {
        return query.select(searchWord1.searchWord)
                .from(searchWord1)
                .where(searchWord1.searchWord.contains(word))
                .orderBy(searchWord1.value.desc())
                .limit(5L)
                .fetch();
    }

    public SearchAmountResponse getSearchAmount(String word) {
        long nicknameAmount = query.selectFrom(user)
                .where(user.nickname.contains(word))
                .fetch().size();
        long emailAmount = query.selectFrom(user)
                .where(user.email.contains(word))
                .fetch().size();
        long templateAmount = query.selectFrom(template)
                .where(template.title.contains(word))
                .fetch().size();
        return new SearchAmountResponse(nicknameAmount, emailAmount, templateAmount);
    }

}
