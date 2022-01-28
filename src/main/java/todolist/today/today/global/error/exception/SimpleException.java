package todolist.today.today.global.error.exception;

import lombok.Getter;
import todolist.today.today.global.error.ErrorCode;
import todolist.today.today.global.error.dto.SimpleErrorResponse;

@Getter
public class SimpleException extends RuntimeException implements GlobalException<SimpleErrorResponse> {

    private final SimpleErrorResponse errorResponse;

    protected SimpleException(ErrorCode errorCode, String reason) {
        super(errorCode.getMessage());
        this.errorResponse = new SimpleErrorResponse(errorCode, reason);
    }

}
