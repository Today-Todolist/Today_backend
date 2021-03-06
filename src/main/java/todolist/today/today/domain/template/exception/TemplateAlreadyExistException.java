package todolist.today.today.domain.template.exception;

import todolist.today.today.global.error.exception.SimpleException;

import static todolist.today.today.global.error.ErrorCode.TEMPLATE_ALREADY_EXIST;

public class TemplateAlreadyExistException extends SimpleException {

    public TemplateAlreadyExistException(String title) {
        super(TEMPLATE_ALREADY_EXIST, "Template (title: " + title + ") already exist");
    }

}
