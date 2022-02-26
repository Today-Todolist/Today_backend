package todolist.today.today.domain.user.exception;

import todolist.today.today.global.error.exception.BasicException;

import static todolist.today.today.global.error.ErrorCode.TODOLIST_CHANGE_IMPOSSIBLE;

public class TodolistChangeImpossibleException extends BasicException {

    public TodolistChangeImpossibleException() {
        super(TODOLIST_CHANGE_IMPOSSIBLE);
    }

}
