package todolist.today.today.domain.user.application

import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification
import todolist.today.today.domain.CheckService
import todolist.today.today.domain.user.dao.CustomUserRepositoryImpl
import todolist.today.today.domain.user.dao.redis.ChangePasswordCertifyRepository
import todolist.today.today.domain.user.dao.redis.SignUpCertifyRepository
import todolist.today.today.domain.user.dto.request.ChangePasswordCertifySendRequest
import todolist.today.today.domain.user.dto.request.SignUpCertifySendRequest
import todolist.today.today.domain.user.exception.UserNotFoundException
import todolist.today.today.infra.mail.MailContentProvider
import todolist.today.today.infra.mail.MailSendFacade

import static todolist.today.today.RequestUtil.makeChangePasswordCertifySendRequest
import static todolist.today.today.RequestUtil.makeSignUpCertifySendRequest

class CertifySendServiceTest extends Specification {

    private CertifySendService certifySendService
    private CheckService checkService = Stub(CheckService)
    private SignUpCertifyRepository signUpCertifyRepository = Stub(SignUpCertifyRepository)
    private ChangePasswordCertifyRepository changePasswordCertifyRepository = Stub(ChangePasswordCertifyRepository)
    private CustomUserRepositoryImpl customUserRepository = Stub(CustomUserRepositoryImpl)
    private MailContentProvider mailContentProvider = Stub(MailContentProvider)
    private MailSendFacade mailSendFacade = Stub(MailSendFacade)
    private PasswordEncoder passwordEncoder = Stub(PasswordEncoder)

    def setup() {
        certifySendService = new CertifySendService(
                checkService,
                signUpCertifyRepository,
                changePasswordCertifyRepository,
                customUserRepository,
                mailContentProvider,
                mailSendFacade,
                passwordEncoder)
    }

    def "test sendSignUpCertify" () {
        given:
        SignUpCertifySendRequest request = makeSignUpCertifySendRequest("", "", "")

        when:
        certifySendService.sendSignUpCertify(request)

        then:
        noExceptionThrown()
    }

    def "test sendChangePasswordCertify" () {
        given:
        final String EMAIL = "today043149@gmail.com"

        ChangePasswordCertifySendRequest request = makeChangePasswordCertifySendRequest(EMAIL, "")
        customUserRepository.findNicknameById(EMAIL) >> "nickname"

        when:
        certifySendService.sendChangePasswordCertify(request)

        then:
        noExceptionThrown()
    }

    def "test sendChangePasswordCertify UserNotFoundException" () {
        given:
        final String EMAIL = "today043149@gmail.com"

        ChangePasswordCertifySendRequest request = makeChangePasswordCertifySendRequest(EMAIL, "")
        customUserRepository.findNicknameById(EMAIL) >> null

        when:
        certifySendService.sendChangePasswordCertify(request)

        then:
        thrown(UserNotFoundException)
    }

}