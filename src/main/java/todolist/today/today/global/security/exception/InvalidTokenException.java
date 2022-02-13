package todolist.today.today.global.security.exception;

import todolist.today.today.global.error.exception.BasicException;

import static todolist.today.today.global.error.ErrorCode.INVALID_TOKEN;

public class InvalidTokenException extends BasicException {

    public InvalidTokenException() {
        super(INVALID_TOKEN);
    }

}
