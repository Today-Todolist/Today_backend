package todolist.today.today.infra.file.exception;

import lombok.extern.slf4j.Slf4j;
import todolist.today.today.global.error.exception.BasicException;

import static todolist.today.today.global.error.ErrorCode.FILE_DELETE_FAILED;

@Slf4j
public class FileDeleteFailedException extends BasicException {

    public FileDeleteFailedException(String path) {
        super(FILE_DELETE_FAILED);
        log.error("Delete Local File Failed (path: " + path + ")");
    }

}
