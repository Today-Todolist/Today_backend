package todolist.today.today.domain.user.exception;

import todolist.today.today.global.error.exception.SimpleException;

import static todolist.today.today.global.error.ErrorCode.LOGIN_FAILED;

public class LoginFailedException extends SimpleException {

    public LoginFailedException() {
        super(LOGIN_FAILED, "User that matches the email and password does not exist");
    }

}
