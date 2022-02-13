package todolist.today.today.global.security.exception;

import todolist.today.today.global.error.exception.SimpleException;

import static todolist.today.today.global.error.ErrorCode.TOO_MANY_REQUEST;

public class TooManyRequestException extends SimpleException {

    public TooManyRequestException(long buckets) {
        super(TOO_MANY_REQUEST, "Currently, you can request " + buckets + " times");
    }

}
