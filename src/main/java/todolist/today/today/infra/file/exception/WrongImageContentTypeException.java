package todolist.today.today.infra.file.exception;

import todolist.today.today.global.error.exception.SimpleException;

import static todolist.today.today.global.error.ErrorCode.WRONG_IMAGE_CONTENT_TYPE;

public class WrongImageContentTypeException extends SimpleException {

    public WrongImageContentTypeException(String contentType) {
        super(WRONG_IMAGE_CONTENT_TYPE,
                "The image content type must be image/png or image/jpeg. But image content type is " + contentType);
    }

}
