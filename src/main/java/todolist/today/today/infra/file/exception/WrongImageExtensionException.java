package todolist.today.today.infra.file.exception;

import todolist.today.today.global.error.exception.SimpleException;

import static todolist.today.today.global.error.ErrorCode.WRONG_IMAGE_EXTENSION;

public class WrongImageExtensionException extends SimpleException {

    public WrongImageExtensionException(String extension) {
        super(WRONG_IMAGE_EXTENSION,
                "The image extension must be jpeg or png or jpg. But image extension is " + extension);
    }

}
