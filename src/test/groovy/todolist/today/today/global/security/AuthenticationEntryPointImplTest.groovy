package todolist.today.today.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.core.AuthenticationException
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.lang.reflect.Field

class AuthenticationEntryPointImplTest extends Specification {

    private AuthenticationEntryPointImpl authenticationEntryPoint =
            new AuthenticationEntryPointImpl()
    private HttpServletRequest request = Stub(HttpServletRequest)
    private HttpServletResponse response = Stub(HttpServletResponse)
    private AuthenticationException authException = Stub(AuthenticationException)

    def setup() {
        Field field = authenticationEntryPoint.getClass().getDeclaredField("objectMapper")
        field.setAccessible(true)
        field.set(authenticationEntryPoint, Stub(ObjectMapper))
    }

    def "test commence" () {
        when:
        authenticationEntryPoint.commence(request, response, authException)

        then:
        noExceptionThrown()
    }

}