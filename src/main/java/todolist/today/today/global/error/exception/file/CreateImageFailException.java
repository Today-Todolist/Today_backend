package todolist.today.today.global.error.exception.file;

import todolist.today.today.global.error.ErrorCode;
import todolist.today.today.global.error.exception.BasicException;

public class CreateImageFailException extends BasicException {

    public CreateImageFailException() {
        super(ErrorCode.CREATE_RANDOM_IMAGE_FAILED);
    }

}
