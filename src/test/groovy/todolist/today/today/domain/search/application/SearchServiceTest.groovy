package todolist.today.today.domain.search.application

import spock.lang.Specification
import todolist.today.today.domain.search.dao.CustomSearchWordRepository
import todolist.today.today.domain.search.dao.SearchWordRepository
import todolist.today.today.domain.search.domain.SearchWord
import todolist.today.today.domain.template.dao.CustomTemplateRepository
import todolist.today.today.domain.template.dao.TemplateRepository
import todolist.today.today.domain.user.dao.CustomUserRepository
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.global.dto.request.PagingRequest

class SearchServiceTest extends Specification {

    SearchService searchService
    CustomSearchWordRepository customSearchWordRepository = Stub()
    CustomUserRepository customUserRepository = Stub()
    CustomTemplateRepository customTemplateRepository = Stub()
    SearchWordRepository searchWordRepository = Stub()
    UserRepository userRepository = Stub()
    TemplateRepository templateRepository = Stub()

    def setup() {
        searchService = new SearchService(customSearchWordRepository,
                customUserRepository,
                customTemplateRepository,
                searchWordRepository,
                userRepository,
                templateRepository)
    }

    def "test getSearchWord" () {
        when:
        searchService.getSearchWord("todolist")

        then:
        noExceptionThrown()
    }

    def "test getSearchAmount" () {
        given:
        final String WORD = "todolist"
        searchWordRepository.findById(WORD) >> searchWord

        when:
        searchService.getSearchAmount(WORD)

        then:
        noExceptionThrown()

        where:
        searchWord << [Optional.of(new SearchWord("WORD")), Optional.empty()]
    }

    def "test getNicknameResult" () {
        when:
        searchService.getNicknameResult("", "todolist", new PagingRequest(0, 1))

        then:
        noExceptionThrown()
    }

    def "test getEmailResult" () {
        when:
        searchService.getEmailResult("", "todolist", new PagingRequest(0, 1))

        then:
        noExceptionThrown()
    }

    def "test getTemplateResult" () {
        when:
        searchService.getTemplateResult("todolist", new PagingRequest(0, 1))

        then:
        noExceptionThrown()
    }

}