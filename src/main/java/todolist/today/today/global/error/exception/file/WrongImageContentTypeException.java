package todolist.today.today.global.error.exception.file;

import todolist.today.today.global.error.ErrorCode;
import todolist.today.today.global.error.exception.SimpleException;

public class WrongImageContentTypeException extends SimpleException {

    public WrongImageContentTypeException(String contentType) {
        super(ErrorCode.WRONG_IMAGE_CONTENT_TYPE,
                "The image content type must be image/png or image/jpeg. But image content type is " + contentType);
    }

}
