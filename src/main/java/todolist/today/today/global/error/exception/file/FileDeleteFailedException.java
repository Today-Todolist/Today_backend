package todolist.today.today.global.error.exception.file;

import lombok.extern.slf4j.Slf4j;
import todolist.today.today.global.error.ErrorCode;
import todolist.today.today.global.error.exception.BasicException;

@Slf4j
public class FileDeleteFailedException extends BasicException {

    public FileDeleteFailedException(String path) {
        super(ErrorCode.FILE_DELETE_FAILED);
        log.error("Delete Local File Failed (path: " + path + ")");
    }

}
