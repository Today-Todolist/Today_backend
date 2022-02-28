package todolist.today.today.domain.user.application

import spock.lang.Specification
import todolist.today.today.domain.user.dao.CustomUserRepositoryImpl

class InfoServiceTest extends Specification {

    private InfoService infoService
    private CustomUserRepositoryImpl customUserRepository = Stub(CustomUserRepositoryImpl)

    def setup() {
        infoService = new InfoService(customUserRepository)
    }

    def "test getMyInfo" () {
        when:
        infoService.getMyInfo("")

        then:
        noExceptionThrown()
    }

    def "test getUserInfo" () {
        when:
        infoService.getUserInfo("", "")

        then:
        noExceptionThrown()
    }

}