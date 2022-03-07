package todolist.today.today.domain.template.exception;

import todolist.today.today.global.error.exception.SimpleException;

import static todolist.today.today.global.error.ErrorCode.TEMPLATE_SUBJECT_ORDER_OUT_OF_RANGE;

public class TemplateSubjectOrderException extends SimpleException {

    public TemplateSubjectOrderException(int order) {
        super(TEMPLATE_SUBJECT_ORDER_OUT_OF_RANGE, "Template Subject Order (order: " + order + ") out of range");
    }

}
