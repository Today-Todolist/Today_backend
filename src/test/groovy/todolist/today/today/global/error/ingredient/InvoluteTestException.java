package todolist.today.today.global.error.ingredient;

import todolist.today.today.global.error.ErrorCode;
import todolist.today.today.global.error.dto.InvoluteErrorResponse;
import todolist.today.today.global.error.exception.InvoluteException;

import java.util.HashMap;

public class InvoluteTestException extends InvoluteException {

    public InvoluteTestException() {
        super(new InvoluteErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, new HashMap<>()));
    }

}
