package todolist.today.today.domain.search.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static todolist.today.today.domain.search.domain.QSearchWord.searchWord1;

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

}
