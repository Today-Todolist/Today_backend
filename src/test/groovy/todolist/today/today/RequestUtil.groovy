package todolist.today.today

import todolist.today.today.domain.user.dto.request.ChangePasswordCertifySendRequest
import todolist.today.today.domain.user.dto.request.LoginRequest
import todolist.today.today.domain.user.dto.request.SignUpCertifySendRequest
import todolist.today.today.domain.user.dto.request.TokenRefreshRequest

import java.lang.reflect.Field

class RequestUtil {

    public static <T> void inputField(T object, String name, Object value) {
        Field field = object.getClass().getDeclaredField(name)
        field.setAccessible(true)
        field.set(object, value)
    }

    static LoginRequest makeLoginRequest(String email, String password) {
        LoginRequest request = new LoginRequest()
        inputField(request, "email", email)
        inputField(request, "password", password)
        return request
    }

    static TokenRefreshRequest makeTokenRefreshRequest(String refresh) {
        TokenRefreshRequest request = new TokenRefreshRequest()
        inputField(request, "refreshToken", refresh)
        return request
    }

    static SignUpCertifySendRequest makeSignUpCertifySendRequest(String email, String password, nickname) {
        SignUpCertifySendRequest request = new SignUpCertifySendRequest()
        inputField(request, "email", email)
        inputField(request, "password", password)
        inputField(request, "nickname", nickname)
        return request
    }

    static ChangePasswordCertifySendRequest makeChangePasswordCertifySendRequest(String email, String newPassword) {
        ChangePasswordCertifySendRequest request = new ChangePasswordCertifySendRequest()
        inputField(request, "email", email)
        inputField(request, "newPassword", newPassword)
        return request
    }

}