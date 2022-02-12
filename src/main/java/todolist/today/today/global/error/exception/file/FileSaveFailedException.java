package todolist.today.today.global.error.exception.file;

import todolist.today.today.global.error.exception.SimpleException;

import java.io.IOException;

import static todolist.today.today.global.error.ErrorCode.FILE_SAVE_FAILED;

public class FileSaveFailedException extends SimpleException {

    public FileSaveFailedException(IOException e) {
        super(FILE_SAVE_FAILED, e.getMessage());
    }

}