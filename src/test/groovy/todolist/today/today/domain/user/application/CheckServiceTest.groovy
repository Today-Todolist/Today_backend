package todolist.today.today.domain.user.application

import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification
import todolist.today.today.domain.template.dao.TemplateRepository
import todolist.today.today.domain.template.exception.TemplateAlreadyExistException
import todolist.today.today.domain.user.dao.CustomUserRepositoryImpl
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.dao.redis.SignUpCertifyRepository
import todolist.today.today.domain.user.exception.AuthenticationFailedException
import todolist.today.today.domain.user.exception.NicknameAlreadyExistException
import todolist.today.today.domain.user.exception.UserAlreadyExistException

class CheckServiceTest extends Specification {

    private CheckService checkService
    private SignUpCertifyRepository signUpCertifyRepository = Stub(SignUpCertifyRepository)
    private UserRepository userRepository = Stub(UserRepository)
    private CustomUserRepositoryImpl customUserRepository = Stub(CustomUserRepositoryImpl)
    private TemplateRepository templateRepository = Stub(TemplateRepository)
    private PasswordEncoder passwordEncoder = Stub(PasswordEncoder)

    def setup() {
        checkService = new CheckService(
                signUpCertifyRepository,
                userRepository,
                customUserRepository,
                templateRepository,
                passwordEncoder)
    }

    def "test checkEmail" () {
        when:
        checkService.checkEmail("today043149@gmail.com")

        then:
        noExceptionThrown()
    }

    def "test checkEmail UserAlreadyExistException" () {
        given:
        final String EMAIL = "today043149@gmail.com"
        signUpCertifyRepository.existsByEmail(EMAIL) >> existsByEmail
        userRepository.existsById(EMAIL) >> true

        when:
        checkService.checkEmail(EMAIL)

        then:
        thrown(UserAlreadyExistException)

        where:
        existsByEmail << [true, false]
    }

    def "test checkNickname" () {
        when:
        checkService.checkNickname("today")

        then:
        noExceptionThrown()
    }

    def "test checkNickname NicknameAlreadyExistException" () {
        given:
        final String NICKNAME = "today"
        signUpCertifyRepository.existsByNickname(NICKNAME) >> existsByNickname
        userRepository.existsByNickname(NICKNAME) >> true

        when:
        checkService.checkNickname(NICKNAME)

        then:
        thrown(NicknameAlreadyExistException)

        where:
        existsByNickname << [true, false]
    }

    def "test checkPassword" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final String PASSOWRD = "password"

        customUserRepository.findPasswordById(USER_ID) >> PASSOWRD
        passwordEncoder.matches(PASSOWRD, PASSOWRD) >> true

        when:
        checkService.checkPassword(USER_ID, PASSOWRD)

        then:
        noExceptionThrown()
    }

    def "test checkPassword AuthenticationFailedException" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final String PASSOWRD = "password"

        customUserRepository.findPasswordById(USER_ID) >> password
        passwordEncoder.matches(PASSOWRD, PASSOWRD) >> false

        when:
        checkService.checkPassword(USER_ID, PASSOWRD)

        then:
        thrown(AuthenticationFailedException)

        where:
        password << [null, "password"]
    }

    def "test checkTemplateTitle" () {
        when:
        checkService.checkTemplateTitle("today043149@gmail.com", "title")

        then:
        noExceptionThrown()
    }

    def "test checkTemplateTitle TemplateAlreadyExistException" () {
        given:
        final String USER_ID = "today043149@gmail.com"
        final String TITLE = "title"
        templateRepository.existsByUserEmailAndTitle(USER_ID, TITLE) >> true

        when:
        checkService.checkTemplateTitle(USER_ID, TITLE)

        then:
        thrown(TemplateAlreadyExistException)
    }

}