package todolist.today.today.global.security.auth

import org.springframework.security.core.userdetails.UserDetails
import spock.lang.Specification
import todolist.today.today.domain.model.entity.User
import todolist.today.today.global.error.exception.security.InvalidTokenException
import todolist.today.today.domain.model.repository.UserRepository

class AuthDetailsServiceTest extends Specification {

    private AuthDetailsService authDetailsService
    private UserRepository userRepository = Stub(UserRepository)

    def setup() {
        authDetailsService = new AuthDetailsService(userRepository)
    }

    def "test return UserDetails" () {
        given:
        User user = User.builder()
                .email("today043149@gmail.com")
                .password("Encrypted password example")
                .nickname("오늘")
                .profile("https://github.com/Today-Todolist")
                .build()
        userRepository.findById(user.getEmail()) >> Optional.of(user)

        when:
        UserDetails userDetails = authDetailsService.loadUserByUsername(user.getEmail())

        then:
        userDetails.getUsername() == user.getEmail()
        userDetails.getAuthorities().isEmpty()
    }

    def "test throw InvalidTokenException" () {
        given:
        final String EMAIL = "today043149@gmail.com"
        userRepository.findById(EMAIL) >> Optional.empty()

        when:
        authDetailsService.loadUserByUsername(EMAIL)

        then:
        thrown(InvalidTokenException)
    }

}