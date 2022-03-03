package todolist.today.today.global.error.exception;

import lombok.Getter;
import todolist.today.today.global.error.ErrorCode;
import todolist.today.today.global.error.dto.BasicErrorResponse;

@Getter
public class BasicException extends RuntimeException implements GlobalException<BasicErrorResponse> {

    private final BasicErrorResponse errorResponse;

    protected BasicException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorResponse = new BasicErrorResponse(errorCode);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }

}
