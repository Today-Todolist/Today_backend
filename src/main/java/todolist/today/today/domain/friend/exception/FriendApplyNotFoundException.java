package todolist.today.today.domain.friend.exception;

import todolist.today.today.global.error.exception.SimpleException;

import static todolist.today.today.global.error.ErrorCode.FRIEND_APPLY_NOT_FOUND;

public class FriendApplyNotFoundException extends SimpleException {

    public FriendApplyNotFoundException(String userId, String myId) {
        super(FRIEND_APPLY_NOT_FOUND, "Friend apply (" + userId + " - " + myId + ") not found");
    }

}
