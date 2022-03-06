package todolist.today.today.domain.template.exception;

import todolist.today.today.global.error.exception.SimpleException;

import static todolist.today.today.global.error.ErrorCode.TEMPLATE_SUBJECT_NOT_FOUND;

public class TemplateSubjectNotFoundException extends SimpleException {

    public TemplateSubjectNotFoundException(String subjectId) {
        super(TEMPLATE_SUBJECT_NOT_FOUND, "Template Subject (id: " + subjectId + ") not found");
    }

}
