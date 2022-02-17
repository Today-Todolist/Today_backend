package todolist.today.today.global.security.service

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import spock.lang.Specification
import todolist.today.today.global.security.auth.AuthDetails

class AuthenticationFacadeTest extends Specification {

    private AuthenticationFacade authenticationFacade = new AuthenticationFacade()

    def cleanup() {
        SecurityContextHolder.clearContext()
    }

    def "test getUserId" () {
        given:
        final String USER_ID = "today"
        AuthDetails details = new AuthDetails(USER_ID)
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(details, "", details.getAuthorities())
        SecurityContextHolder.getContext().setAuthentication(authentication)

        when:
        String user_id = authenticationFacade.getUserId()

        then:
        user_id == USER_ID
    }

}