package todolist.today.today.domain.todolist.exception;

import todolist.today.today.global.error.exception.SimpleException;

import static todolist.today.today.global.error.ErrorCode.TODOLIST_CONTENT_NOT_FOUND;

public class TodolistContentNotFoundException extends SimpleException {

    public TodolistContentNotFoundException(String contentId) {
        super(TODOLIST_CONTENT_NOT_FOUND, "Todolist Content (id: " + contentId + ") not found");
    }

}
