package todolist.today.today.global.error.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import todolist.today.today.global.error.dto.BasicErrorResponse;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BasicException extends RuntimeException implements GlobalException<BasicErrorResponse> {

    private final BasicErrorResponse errorResponse;

}
