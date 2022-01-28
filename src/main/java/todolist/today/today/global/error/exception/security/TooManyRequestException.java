package todolist.today.today.global.error.exception.security;

import todolist.today.today.global.error.ErrorCode;
import todolist.today.today.global.error.exception.SimpleException;

public class TooManyRequestException extends SimpleException {

    public TooManyRequestException(long buckets) {
        super(ErrorCode.TOO_MANY_REQUEST, "Currently, you can request " + buckets + " times");
    }

}
