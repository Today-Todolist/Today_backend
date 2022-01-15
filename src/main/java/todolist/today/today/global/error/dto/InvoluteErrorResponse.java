package todolist.today.today.global.error.dto;

import lombok.Getter;
import todolist.today.today.global.error.ErrorCode;

import java.util.Map;

@Getter
public class InvoluteErrorResponse extends BasicErrorResponse {

    private final Map<String, String> reasons;

    public InvoluteErrorResponse(ErrorCode errorCode, Map<String, String> reasons) {
        super(errorCode);
        this.reasons = reasons;
    }

}
