package todolist.today.today.global.error.exception.security;

import lombok.extern.slf4j.Slf4j;
import todolist.today.today.global.error.ErrorCode;
import todolist.today.today.global.error.exception.BasicException;

@Slf4j
public class ImpossibleToGetIpException extends BasicException {

    public ImpossibleToGetIpException() {
        super(ErrorCode.IMPOSSIBLE_TO_GET_IP);
        log.error("Impossible to get ip");
    }

}
