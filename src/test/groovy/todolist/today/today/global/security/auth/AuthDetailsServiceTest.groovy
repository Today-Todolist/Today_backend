package todolist.today.today.global.security.auth

import org.springframework.security.core.userdetails.UserDetails
import spock.lang.Specification
import todolist.today.today.domain.model.repository.UserRepository
import todolist.today.today.global.error.exception.security.InvalidTokenException

class AuthDetailsServiceTest extends Specification {

    private AuthDetailsService authDetailsService
    private UserRepository userRepository = Stub(UserRepository)

    def setup() {
        authDetailsService = new AuthDetailsService(userRepository)
    }

    def "test return UserDetails" () {
        given:
        final String EMAIL = "today043149@gmail.com"
        userRepository.existsById(EMAIL) >> true

        when:
        UserDetails userDetails = authDetailsService.loadUserByUsername(EMAIL)

        then:
        userDetails.getId() == EMAIL
        userDetails.getAuthorities().isEmpty()
    }

    def "test throw InvalidTokenException" () {
        given:
        final String EMAIL = "today043149@gmail.com"
        userRepository.existsById(EMAIL) >> false

        when:
        authDetailsService.loadUserByUsername(EMAIL)

        then:
        thrown(InvalidTokenException)
    }

}