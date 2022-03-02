package todolist.today.today.domain.user.application

import spock.lang.Specification
import todolist.today.today.domain.user.dao.CustomUserRepositoryImpl
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.exception.UserNotFoundException

class UserInfoServiceTest extends Specification {

    private UserInfoService userInfoService
    private CustomUserRepositoryImpl customUserRepository = Stub(CustomUserRepositoryImpl)
    private UserRepository userRepository = Stub(UserRepository)

    def setup() {
        userInfoService = new UserInfoService(customUserRepository, userRepository)
    }

    def "test getMyInfo" () {
        when:
        userInfoService.getMyInfo("")

        then:
        noExceptionThrown()
    }

    def "test getUserInfo" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        userRepository.existsById(USER_ID) >> true

        when:
        userInfoService.getUserInfo(USER_ID, "")

        then:
        noExceptionThrown()
    }

    def "test getUserInfo UserNotFoundException" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        userRepository.existsById(USER_ID) >> false

        when:
        userInfoService.getUserInfo(USER_ID, "")

        then:
        thrown(UserNotFoundException)
    }

}