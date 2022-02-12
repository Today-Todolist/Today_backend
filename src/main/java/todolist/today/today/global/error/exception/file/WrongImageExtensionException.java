package todolist.today.today.global.error.exception.file;

import todolist.today.today.global.error.ErrorCode;
import todolist.today.today.global.error.exception.SimpleException;

public class WrongImageExtensionException extends SimpleException {

    public WrongImageExtensionException(String extension) {
        super(ErrorCode.WRONG_IMAGE_EXTENSION,
                "The image extension must be jpeg or png or jpg. But image extension is " + extension);
    }

}
