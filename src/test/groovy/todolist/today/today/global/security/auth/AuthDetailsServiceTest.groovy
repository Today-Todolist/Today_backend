package todolist.today.today.global.security.auth

import org.springframework.security.core.userdetails.UserDetails
import spock.lang.Specification
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.global.security.exception.InvalidTokenException

class AuthDetailsServiceTest extends Specification {

    AuthDetailsService authDetailsService
    UserRepository userRepository = Stub()

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