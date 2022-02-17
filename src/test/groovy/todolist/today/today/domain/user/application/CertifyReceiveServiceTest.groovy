package todolist.today.today.domain.user.application

import spock.lang.Specification
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.dao.redis.ChangePasswordCertifyRepository
import todolist.today.today.domain.user.dao.redis.SignUpCertifyRepository
import todolist.today.today.domain.user.domain.User
import todolist.today.today.domain.user.domain.redis.ChangePasswordCertify
import todolist.today.today.domain.user.domain.redis.SignUpCertify
import todolist.today.today.domain.user.exception.UserNotFoundException
import todolist.today.today.domain.user.exception.WrongCertifyException
import todolist.today.today.infra.file.image.ImageUploadFacade


class CertifyReceiveServiceTest extends Specification {

    private CertifyReceiveService certifyReceiveService
    private SignUpCertifyRepository signUpCertifyRepository = Stub(SignUpCertifyRepository)
    private ChangePasswordCertifyRepository changePasswordCertifyRepository = Stub(ChangePasswordCertifyRepository)
    private UserRepository userRepository = Stub(UserRepository)
    private ImageUploadFacade imageUploadFacade = Stub(ImageUploadFacade)

    def setup() {
        certifyReceiveService = new CertifyReceiveService(
                signUpCertifyRepository,
                changePasswordCertifyRepository,
                userRepository,
                imageUploadFacade)
    }

    def "test receiveSignUpCertify" () {
        given:
        final String EMAIL = "today043149@gmail.com"
        final long TOKEN = 1234L

        signUpCertifyRepository.findById(TOKEN) >>
                Optional.of(
                        SignUpCertify.builder()
                                .email(EMAIL)
                                .build()
                )

        when:
        certifyReceiveService.receiveSignUpCertify(EMAIL, TOKEN)

        then:
        noExceptionThrown()
    }

    def "test receiveSignUpCertify WrongCertifyException" () {
        given:
        final String EMAIL = "today043149@gmail.com"
        final long TOKEN = 1234L

        signUpCertifyRepository.findById(TOKEN) >> certify

        when:
        certifyReceiveService.receiveSignUpCertify(EMAIL, TOKEN)

        then:
        thrown(WrongCertifyException)

        where:
        certify << [
                Optional.of(SignUpCertify.builder()
                        .email("")
                        .build()),
                Optional.empty()
        ]
    }

    def "test receiveChangePasswordCertify" () {
        given:
        final String EMAIL = "today043149@gmail.com"
        final long TOKEN = 1234L

        changePasswordCertifyRepository.findById(TOKEN) >>
                Optional.of(
                        ChangePasswordCertify.builder()
                                .email(EMAIL)
                                .password("")
                                .build())

        userRepository.findById(EMAIL) >>
                Optional.of(User.builder().build())

        when:
        certifyReceiveService.receiveChangePasswordCertify(EMAIL, TOKEN)

        then:
        noExceptionThrown()
    }

    def "test receiveChangePasswordCertify WrongCertifyException" () {
        given:
        final String EMAIL = "today043149@gmail.com"
        final long TOKEN = 1234L

        changePasswordCertifyRepository.findById(TOKEN) >> certify

        when:
        certifyReceiveService.receiveChangePasswordCertify(EMAIL, TOKEN)

        then:
        thrown(WrongCertifyException)

        where:
        certify << [
                Optional.of(ChangePasswordCertify.builder()
                        .email("")
                        .build()),
                Optional.empty()
        ]
    }

    def "test receiveChangePasswordCertify UserNotFoundException" () {
        given:
        final String EMAIL = "today043149@gmail.com"
        final long TOKEN = 1234L

        changePasswordCertifyRepository.findById(TOKEN) >>
                Optional.of(
                        ChangePasswordCertify.builder()
                                .email(EMAIL)
                                .password("")
                                .build())

        userRepository.findById(EMAIL) >> Optional.empty()

        when:
        certifyReceiveService.receiveChangePasswordCertify(EMAIL, TOKEN)

        then:
        thrown(UserNotFoundException)
    }

}