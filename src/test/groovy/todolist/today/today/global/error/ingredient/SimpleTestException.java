package todolist.today.today.global.error.ingredient;

import todolist.today.today.global.error.ErrorCode;
import todolist.today.today.global.error.exception.SimpleException;

public class SimpleTestException extends SimpleException {

    public SimpleTestException() {
        super(ErrorCode.INTERNAL_SERVER_ERROR, "test simple exception");
    }

}
