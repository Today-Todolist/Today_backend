package todolist.today.today.domain.search.dao

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification
import todolist.today.today.domain.search.domain.SearchWord

import javax.persistence.EntityManager

@DataJpaTest
class CustomSearchWordRepositoryImplTest extends Specification {

    @Autowired
    private EntityManager em
    private JPAQueryFactory jpaQueryFactory
    private CustomSearchWordRepositoryImpl customSearchWordRepository

    @Autowired
    private SearchWordRepository searchWordRepository

    def setup() {
        jpaQueryFactory = new JPAQueryFactory(em)
        customSearchWordRepository = new CustomSearchWordRepositoryImpl(new JPAQueryFactory(em))
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