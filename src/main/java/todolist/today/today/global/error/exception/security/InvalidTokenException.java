package todolist.today.today.global.error.exception.security;

import todolist.today.today.global.error.ErrorCode;
import todolist.today.today.global.error.dto.BasicErrorResponse;
import todolist.today.today.global.error.exception.BasicException;

public class InvalidTokenException extends BasicException {

    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }

}
