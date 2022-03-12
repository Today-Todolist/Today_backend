package todolist.today.today.domain.todolist.exception;

import todolist.today.today.global.error.exception.SimpleException;

import static todolist.today.today.global.error.ErrorCode.TODOLIST_CONTENT_ORDER_OUT_OF_RANGE;

public class TodolistContentOrderException extends SimpleException {

    public TodolistContentOrderException(int order) {
        super(TODOLIST_CONTENT_ORDER_OUT_OF_RANGE, "Todolist Content Order (order: " + order + ") out of range");
    }

}
