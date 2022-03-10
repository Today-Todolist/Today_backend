package todolist.today.today.domain.user.application

import org.springframework.dao.InvalidDataAccessResourceUsageException
import spock.lang.Specification
import todolist.today.today.domain.user.dao.CustomUserRepositoryImpl
import todolist.today.today.domain.user.exception.UserNotFoundException

class UserInfoServiceTest extends Specification {

    private UserInfoService userInfoService
    private CustomUserRepositoryImpl customUserRepository = Stub(CustomUserRepositoryImpl)

    def setup() {
        userInfoService = new UserInfoService(customUserRepository)
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

        when:
        userInfoService.getUserInfo(USER_ID, "")

        then:
        noExceptionThrown()
    }

    def "test getUserInfo UserNotFoundException" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final String MY_ID = "tomorrow043149@gmail.com"
        customUserRepository.getUserInfo(USER_ID, MY_ID) >> { throw new InvalidDataAccessResourceUsageException("") }

        when:
        userInfoService.getUserInfo(USER_ID, MY_ID)

        then:
        thrown(UserNotFoundException)
    }

}