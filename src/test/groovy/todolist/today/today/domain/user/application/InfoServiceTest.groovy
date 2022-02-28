package todolist.today.today.domain.user.application

import spock.lang.Specification
import todolist.today.today.domain.user.dao.CustomUserRepositoryImpl
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.exception.UserNotFoundException

class InfoServiceTest extends Specification {

    private InfoService infoService
    private CustomUserRepositoryImpl customUserRepository = Stub(CustomUserRepositoryImpl)
    private UserRepository userRepository = Stub(UserRepository)

    def setup() {
        infoService = new InfoService(customUserRepository, userRepository)
    }

    def "test getMyInfo" () {
        when:
        infoService.getMyInfo("")

        then:
        noExceptionThrown()
    }

    def "test getUserInfo" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        userRepository.existsById(USER_ID) >> true

        when:
        infoService.getUserInfo(USER_ID, "")

        then:
        noExceptionThrown()
    }

    def "test getUserInfo UserNotFoundException" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        userRepository.existsById(USER_ID) >> false

        when:
        infoService.getUserInfo(USER_ID, "")

        then:
        thrown(UserNotFoundException)
    }

}