package todolist.today.today.domain.todolist.exception;

import todolist.today.today.global.error.exception.SimpleException;

import static todolist.today.today.global.error.ErrorCode.TODOLIST_SUBJECT_ORDER_OUT_OF_RANGE;

public class TodolistSubjectOrderException extends SimpleException {

    public TodolistSubjectOrderException(int order) {
        super(TODOLIST_SUBJECT_ORDER_OUT_OF_RANGE, "Todolist Subject Order (order: " + order + ") out of range");
    }

}
