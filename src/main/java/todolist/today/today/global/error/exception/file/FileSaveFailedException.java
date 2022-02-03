package todolist.today.today.global.error.exception.file;

import todolist.today.today.global.error.ErrorCode;
import todolist.today.today.global.error.exception.SimpleException;

import java.io.IOException;

public class FileSaveFailedException extends SimpleException {

    public FileSaveFailedException(IOException e) {
        super(ErrorCode.FILE_SAVE_FAILED, e.getMessage());
    }

}