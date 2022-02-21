package todolist.today.today.domain.user.application

import io.jsonwebtoken.Claims
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification
import todolist.today.today.domain.user.dao.CustomUserRepositoryImpl
import todolist.today.today.domain.user.dao.UserRepository
import todolist.today.today.domain.user.dto.request.LoginRequest
import todolist.today.today.domain.user.dto.request.TokenRefreshRequest
import todolist.today.today.domain.user.dto.response.LoginResponse
import todolist.today.today.domain.user.dto.response.TokenRefreshResponse
import todolist.today.today.domain.user.exception.LoginFailedException
import todolist.today.today.domain.user.exception.TokenRefreshException
import todolist.today.today.global.security.service.JwtTokenProvider

import static todolist.today.today.RequestUtil.makeLoginRequest
import static todolist.today.today.RequestUtil.makeTokenRefreshRequest

class AuthServiceTest extends Specification {

    private AuthService authService
    private PasswordEncoder passwordEncoder = Stub(PasswordEncoder)
    private JwtTokenProvider jwtTokenProvider = Stub(JwtTokenProvider)
    private UserRepository userRepository = Stub(UserRepository)
    private CustomUserRepositoryImpl customUserRepository = Stub(CustomUserRepositoryImpl)

    def setup() {
        authService = new AuthService(passwordEncoder, jwtTokenProvider, userRepository, customUserRepository)
    }

    def "test login" () {
        given:
        final String EMAIL = "today043149@gmail.com"
        final String PASSWORD = "asdf"
        final String ACCESS = "accessToken"
        final String REFRESH = "refreshToken"

        LoginRequest request = makeLoginRequest(EMAIL, PASSWORD)

        customUserRepository.findPasswordById(EMAIL) >> PASSWORD
        passwordEncoder.matches(PASSWORD, PASSWORD) >> true

        jwtTokenProvider.generateAccessToken(EMAIL) >> ACCESS
        jwtTokenProvider.generateRefreshToken(EMAIL) >> REFRESH

        when:
        LoginResponse response = authService.login(request)

        then:
        response.getAccessToken() == ACCESS
        response.getRefreshToken() == REFRESH
    }

    def "test login LoginFailedException" () {
        given:
        final String EMAIL = "today043149@gmail.com"

        LoginRequest request = makeLoginRequest(EMAIL, password)

        customUserRepository.findPasswordById(EMAIL) >> password
        passwordEncoder.matches(password, password) >> false

        when:
        authService.login(request)

        then:
        thrown(LoginFailedException)

        where:
        password << [null, "asdf"]
    }

    def "test tokenRefresh" () {
        given:
        final String ACCESS = "access"
        final String REFRESH = "refresh"
        final String ID = "today043149@gmail.com"

        TokenRefreshRequest request = makeTokenRefreshRequest(REFRESH)
        Claims body = Stub(Claims)
        jwtTokenProvider.getBody(REFRESH) >> body
        jwtTokenProvider.isRefresh(body) >> true
        jwtTokenProvider.getId(body) >> ID
        userRepository.existsById(ID) >> true
        jwtTokenProvider.generateAccessToken(ID) >> ACCESS

        when:
        TokenRefreshResponse response = authService.tokenRefresh(request)

        then:
        response.getAccessToken() == ACCESS
    }

    def "test tokenRefresh TokenRefreshException" () {
        given:
        final String REFRESH = "refresh"
        final String ID = "today043149@gmail.com"

        TokenRefreshRequest request = makeTokenRefreshRequest(REFRESH)
        Claims body = Stub(Claims)
        jwtTokenProvider.getBody(REFRESH) >> body
        jwtTokenProvider.isRefresh(body) >> isRefresh
        jwtTokenProvider.getId(body) >> ID
        userRepository.existsById(ID) >> false

        when:
        authService.tokenRefresh(request)

        then:
        thrown(TokenRefreshException)

        where:
        isRefresh << [false, true]
    }

}