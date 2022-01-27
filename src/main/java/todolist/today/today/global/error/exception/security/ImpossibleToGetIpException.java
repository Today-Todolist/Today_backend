package todolist.today.today.global.error.exception.security;

import todolist.today.today.global.error.ErrorCode;
import todolist.today.today.global.error.dto.BasicErrorResponse;
import todolist.today.today.global.error.exception.BasicException;

public class ImpossibleToGetIpException extends BasicException {

    public ImpossibleToGetIpException() {
        super(new BasicErrorResponse(ErrorCode.IMPOSSIBLE_TO_GET_IP));
    }

}
