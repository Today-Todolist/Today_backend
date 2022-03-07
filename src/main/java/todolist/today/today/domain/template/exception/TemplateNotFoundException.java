package todolist.today.today.domain.template.exception;

import todolist.today.today.global.error.exception.SimpleException;

import static todolist.today.today.global.error.ErrorCode.TEMPLATE_NOT_FOUND;

public class TemplateNotFoundException extends SimpleException {

    public TemplateNotFoundException(String templateId) {
        super(TEMPLATE_NOT_FOUND, "Template (id: " + templateId + ") not found");
    }

}
