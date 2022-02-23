package todolist.today.today.domain.user.exception;

import todolist.today.today.global.error.exception.SimpleException;

import static todolist.today.today.global.error.ErrorCode.AUTHENTICATION_FAILED;

public class AuthenticationFailedException extends SimpleException {

    public AuthenticationFailedException() {
        super(AUTHENTICATION_FAILED, "The authentication information is wrong");
    }

}
