package todolist.today.today.global.error.ingredient;

import todolist.today.today.global.error.ErrorCode;
import todolist.today.today.global.error.exception.BasicException;

public class BasicTestException extends BasicException {

    public BasicTestException() {
        super(ErrorCode.INTERNAL_SERVER_ERROR);
    }

}
