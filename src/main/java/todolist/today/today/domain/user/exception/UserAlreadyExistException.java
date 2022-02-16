package todolist.today.today.domain.user.exception;

import todolist.today.today.global.error.exception.SimpleException;

import static todolist.today.today.global.error.ErrorCode.USER_ALREADY_EXIST;

public class UserAlreadyExistException extends SimpleException {

    public UserAlreadyExistException(String id) {
        super(USER_ALREADY_EXIST, "User (id: " + id + ") already exist");
    }

}
