package todolist.today.today.domain.todolist.exception;

import todolist.today.today.global.error.exception.SimpleException;

import static todolist.today.today.global.error.ErrorCode.TODOLIST_SUBJECT_NOT_FOUND;

public class TodolistSubjectNotFoundException extends SimpleException {

    public TodolistSubjectNotFoundException(String subjectId) {
        super(TODOLIST_SUBJECT_NOT_FOUND, "Todolist Subject (id: " + subjectId + ") not found");
    }

}