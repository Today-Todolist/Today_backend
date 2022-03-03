package todolist.today.today.domain.friend.exception;

import todolist.today.today.global.error.exception.SimpleException;

import static todolist.today.today.global.error.ErrorCode.FRIEND_ALREADY_EXIST;

public class FriendAlreadyExistsException extends SimpleException {

    public FriendAlreadyExistsException(String userId, String myId) {
        super(FRIEND_ALREADY_EXIST, "Friend (" + userId + " - " + myId + ") already exist");
    }

}
