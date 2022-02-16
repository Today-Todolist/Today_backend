package todolist.today.today.domain.user.exception;

import todolist.today.today.global.error.ErrorCode;
import todolist.today.today.global.error.exception.SimpleException;

public class WrongCertifyException extends SimpleException {

    public WrongCertifyException() {
        super(ErrorCode.WRONG_CERTIFY, "Time is up or the authentication information is wrong");
    }

}
