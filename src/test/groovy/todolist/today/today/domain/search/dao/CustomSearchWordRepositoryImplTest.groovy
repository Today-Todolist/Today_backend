package todolist.today.today.domain.search.dao

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import spock.lang.Specification
import todolist.today.today.domain.search.domain.SearchWord
import todolist.today.today.global.config.QueryDslConfig

@DataJpaTest
@Import(QueryDslConfig)
class CustomSearchWordRepositoryImplTest extends Specification {

    @Autowired
    JPAQueryFactory jpaQueryFactory
    CustomSearchWordRepositoryImpl customSearchWordRepository

    @Autowired
    SearchWordRepository searchWordRepository

    def setup() {
        customSearchWordRepository = new CustomSearchWordRepositoryImpl(jpaQueryFactory)
    }

    def "test getTemplateSearchResult" () {
        given:
        SearchWord searchWord = new SearchWord("word")
        searchWordRepository.save(searchWord)

        when:
        List<String> response = customSearchWordRepository.getSearchWord("or")

        then:
        response.size() == 1
        response.get(0) == searchWord.getSearchWord()
    }

}