package todolist.today.today.global.security.filter

import io.jsonwebtoken.Claims
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import spock.lang.Specification
import todolist.today.today.global.security.exception.InvalidTokenException
import todolist.today.today.global.security.service.JwtTokenProvider

import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtTokenFilterTest extends Specification {

    private JwtTokenFilter jwtTokenFilter
    private JwtTokenProvider jwtTokenProvider = Stub(JwtTokenProvider)
    private HttpServletRequest request = Stub(HttpServletRequest)
    private HttpServletResponse response = Stub(HttpServletResponse)
    private FilterChain chain = Stub(FilterChain)
    private Claims body = Stub(Claims)
    private Authentication authentication = Stub(Authentication)

    def setup() {
        jwtTokenFilter = new JwtTokenFilter(jwtTokenProvider)
    }

    def "test tokenExists" () {
        given:
        jwtTokenProvider.resolveToken(request) >> Optional.of("token")
        jwtTokenProvider.getBody("token") >> body
        jwtTokenProvider.getAuthentication(body) >> authentication

        when:
        jwtTokenFilter.doFilterInternal(request, response, chain)

        then:
        SecurityContextHolder.getContext().getAuthentication() == authentication
    }

    def "test tokenNonExists" () {
        given:
        jwtTokenProvider.resolveToken(request) >> Optional.empty()

        when:
        jwtTokenFilter.doFilterInternal(request, response, chain)

        then:
        SecurityContextHolder.getContext().getAuthentication() != authentication
    }

    def "test tokenExists but NotAccess" () {
        given:
        jwtTokenProvider.resolveToken(request) >> Optional.of("token")
        jwtTokenProvider.getBody("token") >> body
        jwtTokenProvider.getAuthentication(body) >> { throw new InvalidTokenException() }

        when:
        jwtTokenFilter.doFilterInternal(request, response, chain)

        then:
        thrown(InvalidTokenException)
        SecurityContextHolder.getContext().getAuthentication() != authentication
    }

}