package todolist.today.today.domain.friend.application

import spock.lang.Specification
import todolist.today.today.domain.friend.dao.CustomFriendRepositoryImpl
import todolist.today.today.domain.friend.dao.FriendApplyRepository
import todolist.today.today.domain.friend.dao.FriendRepository
import todolist.today.today.domain.check.application.CheckService
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.domain.User
import todolist.today.today.domain.user.exception.UserNotFoundException

class FriendSettingServiceTest extends Specification {

    private FriendSettingService friendSettingService
    private UserRepository userRepository = Stub(UserRepository)
    private CustomFriendRepositoryImpl customFriendRepository = Stub(CustomFriendRepositoryImpl)
    private FriendRepository friendRepository = Stub(FriendRepository)
    private FriendApplyRepository friendApplyRepository = Stub(FriendApplyRepository)
    private CheckService checkService = Stub(CheckService)

    def setup() {
        friendSettingService = new FriendSettingService(userRepository,
                customFriendRepository,
                friendRepository,
                friendApplyRepository,
                checkService)
    }

    def "test makeFriendApply" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        userRepository.findById(USER_ID) >> Optional.of(new User())

        when:
        friendSettingService.makeFriendApply(USER_ID, USER_ID)

        then:
        noExceptionThrown()
    }

    def "test makeFriendApply UserNotFoundException" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final String MY_ID = "tomorrow043149@gmail.com"
        userRepository.findById(USER_ID) >> Optional.empty()
        userRepository.findById(MY_ID) >> Optional.empty()

        when:
        friendSettingService.makeFriendApply(USER_ID, USER_ID)

        then:
        thrown(UserNotFoundException)

        where:
        optional << [Optional.of(new User()), Optional.empty()]
    }

    def "test deleteFriend" () {
        given:
        final String USER_ID = "today043149@gmail.com"

        when:
        friendSettingService.deleteFriend(USER_ID, USER_ID)

        then:
        noExceptionThrown()
    }

    def "test makeFriend" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        userRepository.findById(USER_ID) >> Optional.of(new User())

        when:
        friendSettingService.makeFriend(USER_ID, USER_ID)

        then:
        noExceptionThrown()
    }

    def "test makeFriend UserNotFoundException" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final String MY_ID = "tomorrow043149@gmail.com"
        userRepository.findById(USER_ID) >> Optional.empty()
        userRepository.findById(MY_ID) >> Optional.empty()

        when:
        friendSettingService.makeFriend(USER_ID, USER_ID)

        then:
        thrown(UserNotFoundException)

        where:
        optional << [Optional.of(new User()), Optional.empty()]
    }

    def "test deleteFriendApply" () {
        given:
        final String USER_ID = "today043149@gmail.com"

        when:
        friendSettingService.deleteFriendApply(USER_ID, USER_ID)

        then:
        noExceptionThrown()
    }

}