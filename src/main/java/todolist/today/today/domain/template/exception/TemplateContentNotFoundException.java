package todolist.today.today.domain.template.exception;

import todolist.today.today.global.error.exception.SimpleException;

import static todolist.today.today.global.error.ErrorCode.TEMPLATE_CONTENT_NOT_FOUND;

public class TemplateContentNotFoundException extends SimpleException {

    public TemplateContentNotFoundException(String contentId) {
        super(TEMPLATE_CONTENT_NOT_FOUND, "Template Content (id: " + contentId + ") not found");
    }

}
