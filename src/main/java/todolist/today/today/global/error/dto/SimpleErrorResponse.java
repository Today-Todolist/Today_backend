package todolist.today.today.global.error.dto;

import lombok.Getter;
import todolist.today.today.global.error.ErrorCode;

@Getter
public class SimpleErrorResponse extends BasicErrorResponse {

    private final String reason;

    private SimpleErrorResponse(ErrorCode errorCode, String reason) {
        super(errorCode);
        this.reason = reason;
    }

    public static SimpleErrorResponse from(ErrorCode errorCode, String reason) {
        return new SimpleErrorResponse(errorCode, reason);
    }

}
