package todolist.today.today.global.error.exception;

import lombok.Getter;
import todolist.today.today.global.error.ErrorCode;
import todolist.today.today.global.error.dto.InvoluteErrorResponse;

import java.util.Map;

@Getter
public class InvoluteException extends RuntimeException implements GlobalException<InvoluteErrorResponse> {

    private final InvoluteErrorResponse errorResponse;

    protected InvoluteException(ErrorCode errorCode, Map<String, String> reason) {
        super(errorCode.getMessage());
        this.errorResponse = new InvoluteErrorResponse(errorCode, reason);
    }

}
