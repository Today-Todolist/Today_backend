package todolist.today.today

import todolist.today.today.domain.user.dto.request.LoginRequest
import todolist.today.today.domain.user.dto.request.TokenRefreshRequest

import java.lang.reflect.Field

class RequestUtil {

    private static <T> void inputField(T object, String name, Object value) {
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
        TokenRefreshRequest request = new TokenRefreshRequest();
        inputField(request, "refreshToken", refresh)
        return request
    }

}