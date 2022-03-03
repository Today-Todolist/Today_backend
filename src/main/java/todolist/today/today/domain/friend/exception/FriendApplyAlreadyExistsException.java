package todolist.today.today.domain.friend.exception;

import todolist.today.today.global.error.exception.SimpleException;

import static todolist.today.today.global.error.ErrorCode.FRIEND_APPLY_ALREADY_EXIST;

public class FriendApplyAlreadyExistsException extends SimpleException {

    public FriendApplyAlreadyExistsException(String userId, String myId) {
        super(FRIEND_APPLY_ALREADY_EXIST, "Friend apply (" + userId + " - " + myId + ") already exist");
    }

}
