package todolist.today.today.domain.user.exception;

import todolist.today.today.global.error.exception.SimpleException;

import static todolist.today.today.global.error.ErrorCode.EMAIL_ALREADY_EXIST;

public class EmailAlreadyExistException extends SimpleException {

    public EmailAlreadyExistException(String email) {
        super(EMAIL_ALREADY_EXIST, "Email (" + email + ") already exist");
    }

}
