package todolist.today.today.domain.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.today.today.domain.user.dao.CustomUserRepositoryImpl;
import todolist.today.today.domain.user.dto.request.LoginRequest;
import todolist.today.today.domain.user.dto.response.LoginResponse;
import todolist.today.today.domain.user.exception.LoginFailedException;
import todolist.today.today.global.security.service.JwtTokenProvider;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final CustomUserRepositoryImpl customUserRepository;

    public LoginResponse login(LoginRequest request) {
        String userId = request.getEmail();

        String password = customUserRepository.findPasswordById(userId);
        if(password == null || !passwordEncoder.matches(request.getPassword(), password)) {
            throw new LoginFailedException();
        }

        String access = jwtTokenProvider.generateAccessToken(userId);
        String refresh = jwtTokenProvider.generateRefreshToken(userId);

        return new LoginResponse(access, refresh);
    }

}
