package todolist.today.today.global.error.exception.file;

import lombok.extern.slf4j.Slf4j;
import todolist.today.today.global.error.ErrorCode;
import todolist.today.today.global.error.exception.BasicException;

@Slf4j
public class CreateImageFailException extends BasicException {

    public CreateImageFailException() {
        super(ErrorCode.CREATE_RANDOM_IMAGE_FAILED);
        log.error("Create Random Image Failed");
    }

}
