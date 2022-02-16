package todolist.today.today.domain.user.exception;

import todolist.today.today.global.error.exception.BasicException;

import static todolist.today.today.global.error.ErrorCode.TOKEN_REFRESH_FAILED;

public class TokenRefreshException extends BasicException {

    public TokenRefreshException() {
        super(TOKEN_REFRESH_FAILED);
    }

}
