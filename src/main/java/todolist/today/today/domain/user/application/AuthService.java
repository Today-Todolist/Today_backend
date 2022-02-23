package todolist.today.today.domain.user.application;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.today.today.domain.user.dao.UserRepository;
import todolist.today.today.domain.user.dto.request.LoginRequest;
import todolist.today.today.domain.user.dto.request.TokenRefreshRequest;
import todolist.today.today.domain.user.dto.response.LoginResponse;
import todolist.today.today.domain.user.dto.response.TokenRefreshResponse;
import todolist.today.today.domain.user.exception.TokenRefreshException;
import todolist.today.today.global.security.service.JwtTokenProvider;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    private final CheckService checkService;
    private final UserRepository userRepository;

    public LoginResponse login(LoginRequest request) {
        String userId = request.getEmail();

        checkService.checkPassword(userId, request.getPassword());

        String access = jwtTokenProvider.generateAccessToken(userId);
        String refresh = jwtTokenProvider.generateRefreshToken(userId);
        return new LoginResponse(access, refresh);
    }

    public TokenRefreshResponse tokenRefresh(TokenRefreshRequest request) {
        String refresh = request.getRefreshToken();

        Claims body = jwtTokenProvider.getBody(refresh);
        if (!jwtTokenProvider.isRefresh(body)) {
            throw new TokenRefreshException();
        }

        String id = jwtTokenProvider.getId(body);
        if (!userRepository.existsById(id)) {
            throw new TokenRefreshException();
        }

        String access = jwtTokenProvider.generateAccessToken(id);
        return new TokenRefreshResponse(access);
    }

}
