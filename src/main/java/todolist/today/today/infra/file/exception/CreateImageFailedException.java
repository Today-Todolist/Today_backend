package todolist.today.today.infra.file.exception;

import lombok.extern.slf4j.Slf4j;
import todolist.today.today.global.error.exception.BasicException;

import static todolist.today.today.global.error.ErrorCode.CREATE_RANDOM_IMAGE_FAILED;

@Slf4j
public class CreateImageFailedException extends BasicException {

    public CreateImageFailedException() {
        super(CREATE_RANDOM_IMAGE_FAILED);
        log.error("Create Random Image Failed");
    }

}
