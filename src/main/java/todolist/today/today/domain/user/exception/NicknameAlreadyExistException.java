package todolist.today.today.domain.user.exception;

import todolist.today.today.global.error.exception.SimpleException;

import static todolist.today.today.global.error.ErrorCode.NICKNAME_ALREADY_EXIST;

public class NicknameAlreadyExistException extends SimpleException {

    public NicknameAlreadyExistException(String nickname) {
        super(NICKNAME_ALREADY_EXIST, "Nickname (" + nickname + ") already exist");
    }

}
