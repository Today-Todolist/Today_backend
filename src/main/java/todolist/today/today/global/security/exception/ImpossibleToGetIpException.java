package todolist.today.today.global.security.exception;

import lombok.extern.slf4j.Slf4j;
import todolist.today.today.global.error.exception.BasicException;

import static todolist.today.today.global.error.ErrorCode.IMPOSSIBLE_TO_GET_IP;

@Slf4j
public class ImpossibleToGetIpException extends BasicException {

    public ImpossibleToGetIpException() {
        super(IMPOSSIBLE_TO_GET_IP);
        log.error("Impossible to get ip");
    }

}
