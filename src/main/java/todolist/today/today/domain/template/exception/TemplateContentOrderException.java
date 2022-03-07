package todolist.today.today.domain.template.exception;

import todolist.today.today.global.error.exception.SimpleException;

import static todolist.today.today.global.error.ErrorCode.TEMPLATE_CONTENT_ORDER_OUT_OF_RANGE;

public class TemplateContentOrderException extends SimpleException {

    public TemplateContentOrderException(int order) {
        super(TEMPLATE_CONTENT_ORDER_OUT_OF_RANGE, "Template Content Order (order: " + order + ") out of range");
    }

}
