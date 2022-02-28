package todolist.today.today.domain.user.application

import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification
import todolist.today.today.domain.todolist.dao.TodolistRepository
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.domain.User
import todolist.today.today.domain.user.dto.request.ChangeNicknameRequest
import todolist.today.today.domain.user.dto.request.ChangePasswordRequest
import todolist.today.today.domain.user.dto.request.DeleteUserRequest
import todolist.today.today.domain.user.dto.request.ResetTodolistRequest
import todolist.today.today.domain.user.exception.UserNotFoundException
import todolist.today.today.infra.file.image.ImageUploadFacade

import static todolist.today.today.RequestUtil.*

class SettingServiceTest extends Specification {

    private SettingService settingService
    private UserRepository userRepository = Stub(UserRepository)
    private TodolistRepository todolistRepository = Stub(TodolistRepository)
    private ImageUploadFacade imageUploadFacade = Stub(ImageUploadFacade)
    private CheckService checkService = Stub(CheckService)

    def setup() {
        settingService = new SettingService(
                userRepository,
                todolistRepository,
                imageUploadFacade,
                checkService)
    }

    def "test changeProfile" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        userRepository.findById(USER_ID) >> Optional.of(new User())

        when:
        settingService.changeProfile(USER_ID, Stub(MultipartFile))

        then:
        noExceptionThrown()
    }

    def "test changeProfile UserNotFoundException" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        userRepository.findById(USER_ID) >> Optional.empty()

        when:
        settingService.changeProfile(USER_ID, Stub(MultipartFile))

        then:
        thrown(UserNotFoundException)
    }

    def "test changeNickname" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        ChangeNicknameRequest request = makeChangeNicknameRequest("")
        userRepository.findById(USER_ID) >> Optional.of(new User())

        when:
        settingService.changeNickname(USER_ID, request)

        then:
        noExceptionThrown()
    }

    def "test changeNickname UserNotFoundException" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        ChangeNicknameRequest request = makeChangeNicknameRequest("")
        userRepository.findById(USER_ID) >> Optional.empty()

        when:
        settingService.changeNickname(USER_ID, request)

        then:
        thrown(UserNotFoundException)
    }

    def "test changePassword" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        ChangePasswordRequest request = makeChangePasswordRequest("", "")
        userRepository.findById(USER_ID) >> Optional.of(new User())

        when:
        settingService.changePassword(USER_ID, request)

        then:
        noExceptionThrown()
    }

    def "test changePassword UserNotFoundException" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        ChangePasswordRequest request = makeChangePasswordRequest("", "")
        userRepository.findById(USER_ID) >> Optional.empty()

        when:
        settingService.changePassword(USER_ID, request)

        then:
        thrown(UserNotFoundException)
    }

    def "test changeEditAvailability" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        userRepository.findById(USER_ID) >> Optional.of(new User())

        when:
        settingService.changeEditAvailability(USER_ID, true)

        then:
        noExceptionThrown()
    }

    def "test changeEditAvailability UserNotFoundException" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        userRepository.findById(USER_ID) >> Optional.empty()

        when:
        settingService.changeEditAvailability(USER_ID, true)

        then:
        thrown(UserNotFoundException)
    }

    def "test resetTodolist" () {
        given:
        ResetTodolistRequest request = makeResetTodolistRequest("")

        when:
        settingService.resetTodolist("", request)

        then:
        noExceptionThrown()
    }

    def "test deleteUser" () {
        given:
        DeleteUserRequest request = makeDeleteUserRequest("")

        when:
        settingService.deleteUser("", request)

        then:
        noExceptionThrown()
    }

}