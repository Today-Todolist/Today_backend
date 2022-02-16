package todolist.today.today.domain.user.exception;

import todolist.today.today.global.error.exception.SimpleException;

import static todolist.today.today.global.error.ErrorCode.USER_NOT_FOUND;

public class UserNotFoundException extends SimpleException {

    public UserNotFoundException(String id) {
        super(USER_NOT_FOUND, "User (id: " + id + ") not found");
    }

}
