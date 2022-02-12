package todolist.today.today.global.security

import io.jsonwebtoken.Claims
import org.springframework.security.core.Authentication
import spock.lang.Specification
import todolist.today.today.domain.model.entity.User
import todolist.today.today.global.error.exception.security.InvalidTokenException
import todolist.today.today.global.security.auth.AuthDetails
import todolist.today.today.global.security.auth.AuthDetailsService

import javax.servlet.http.HttpServletRequest

class JwtTokenProviderTest extends Specification {

    private JwtTokenProvider jwtTokenProvider
    private JwtProperties jwtProperties = Stub(JwtProperties)
    private AuthDetailsService authDetailsService = Stub(AuthDetailsService)
    private final String SECRET = "asdf"
    private final Long ACCESS_EXP = 1000L
    private final Long REFRESH_EXP = 2000L

    def setup() {
        jwtTokenProvider = new JwtTokenProvider(jwtProperties, authDetailsService)
        jwtProperties.getSecret() >> SECRET
        jwtProperties.getAccessExp() >> ACCESS_EXP
        jwtProperties.getRefreshExp() >> REFRESH_EXP
    }

    def "test generateAccessToken and getBody and getId" () {
        given:
        final String ID = "today"

        when:
        String accessToken = jwtTokenProvider.generateAccessToken(ID)
        Claims body= jwtTokenProvider.getBody(accessToken)
        String tokenId = jwtTokenProvider.getId(body)

        then:
        tokenId == ID
    }

    def "test generateRefreshToken and getBody and isRefresh and getId" () {
        given:
        final String id = "today"

        when:
        String refreshToken = jwtTokenProvider.generateRefreshToken(id)
        Claims body= jwtTokenProvider.getBody(refreshToken)
        boolean isRefresh = jwtTokenProvider.isRefresh(refreshToken)
        String tokenId = jwtTokenProvider.getId(body)

        then:
        isRefresh
        tokenId == id
    }

    def "test getBody throw InvalidTokenException" () {
        when:
        jwtTokenProvider.getBody("starange token")

        then:
        thrown(InvalidTokenException)
    }

    def "test resolveToken" () {
        given:
        HttpServletRequest request = Stub(HttpServletRequest)
        request.getHeader("Authorization") >> bearerToken

        when:
        Optional<String> token = jwtTokenProvider.resolveToken(request)

        then:
        token == optionalToken

        where:
        bearerToken || optionalToken
        null || Optional.empty()
        "token" || Optional.empty()
        "Bearer token" || Optional.of("token")
    }

    def "test generateAccessToken and generategetAuthentication and isAccess when access token" () {
        given:
        final String EMAIL = "today043149@gmail.com"
        AuthDetails authDetails = new AuthDetails(EMAIL)
        authDetailsService.loadUserByUsername(EMAIL) >> authDetails

        when:
        String token = jwtTokenProvider.generateAccessToken(EMAIL)
        Claims body = jwtTokenProvider.getBody(token)
        Authentication authentication = jwtTokenProvider.getAuthentication(body)

        then:
        (AuthDetails)authentication.getPrincipal() == authDetails
        authentication.getCredentials() == ""
        authentication.getAuthorities().isEmpty()
    }

    def "test generateRefreshToken and generategetAuthentication and isAccess when refresh token" () {
        when:
        String token = jwtTokenProvider.generateRefreshToken("today")
        Claims body = jwtTokenProvider.getBody(token)
        jwtTokenProvider.getAuthentication(body)

        then:
        thrown(InvalidTokenException)
    }

}