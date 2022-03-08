package todolist.today.today

import todolist.today.today.domain.template.dto.request.*
import todolist.today.today.domain.user.dto.request.*

import java.lang.reflect.Field

class RequestUtil {

    static <T> void inputField(T object, String name, Object value) {
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

    static SignUpCertifySendRequest makeSignUpCertifySendRequest(String email, String password, String nickname) {
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

    static CheckPasswordRequest makeCheckPasswordRequest(String password) {
        CheckPasswordRequest request = new CheckPasswordRequest()
        inputField(request, "password", password)
        return request
    }

    static ChangeNicknameRequest makeChangeNicknameRequest(String newNickname) {
        ChangeNicknameRequest request = new ChangeNicknameRequest()
        inputField(request, "newNickname", newNickname)
        return request
    }

    static ChangePasswordRequest makeChangePasswordRequest(String password, String newPassword) {
        ChangePasswordRequest request = new ChangePasswordRequest()
        inputField(request, "password", password)
        inputField(request, "newPassword", newPassword)
        return request
    }

    static ResetTodolistRequest makeResetTodolistRequest(String password) {
        ResetTodolistRequest request = new ResetTodolistRequest()
        inputField(request, "password", password)
        return request
    }

    static DeleteUserRequest makeDeleteUserRequest(String password) {
        DeleteUserRequest request = new DeleteUserRequest()
        inputField(request, "password", password)
        return request
    }

    static TemplateCreateRequest makeTemplateCreateRequest(String title, int length) {
        TemplateCreateRequest request = new TemplateCreateRequest()
        inputField(request, "title", title)
        inputField(request, "length", length)
        return request
    }

    static TemplateContentCreateRequest makeTemplateContentCreateRequest(String id, String content) {
        TemplateContentCreateRequest request = new TemplateContentCreateRequest()
        inputField(request, "id", id)
        inputField(request, "content", content)
        return request
    }

    static TemplateContentChangeRequest makeTemplateContentChangeRequest(String content) {
        TemplateContentChangeRequest request = new TemplateContentChangeRequest()
        inputField(request, "content", content)
        return request
    }

    static TemplateContentOrderRequest makeTemplateContentOrderRequest(int order) {
        TemplateContentOrderRequest request = new TemplateContentOrderRequest()
        inputField(request, "order", order)
        return request
    }

    static TemplateSubjectCreateRequest makeTemplateSubjectCreateRequest(String id, int day, String subject) {
        TemplateSubjectCreateRequest request = new TemplateSubjectCreateRequest()
        inputField(request, "id", id)
        inputField(request, "day", day)
        inputField(request, "subject", subject)
        return request
    }

    static TemplateSubjectChangeRequest makeTemplateSubjectChangeRequest(String subject) {
        TemplateSubjectChangeRequest request = new TemplateSubjectChangeRequest()
        inputField(request, "subject", subject)
        return request
    }

    static TemplateSubjectOrderRequest makeTemplateSubjectOrderRequest(int order) {
        TemplateSubjectOrderRequest request = new TemplateSubjectOrderRequest()
        inputField(request, "order", order)
        return request
    }

}