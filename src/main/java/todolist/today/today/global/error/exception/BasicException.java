package todolist.today.today.global.error.exception;

import lombok.Getter;
import todolist.today.today.global.error.ErrorCode;

@Getter
public class BasicException extends RuntimeException {

    private final ErrorCode errorCode;

    public BasicException (ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
