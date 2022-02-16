package todolist.today.today.domain.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import todolist.today.today.domain.user.application.AuthService;
import todolist.today.today.domain.user.dto.request.LoginRequest;
import todolist.today.today.domain.user.dto.request.TokenRefreshRequest;
import todolist.today.today.domain.user.dto.response.LoginResponse;
import todolist.today.today.domain.user.dto.response.TokenRefreshResponse;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/token-refresh")
    public TokenRefreshResponse tokenRefresh(@Valid @RequestBody TokenRefreshRequest request) {
        return authService.tokenRefresh(request);
    }

}
