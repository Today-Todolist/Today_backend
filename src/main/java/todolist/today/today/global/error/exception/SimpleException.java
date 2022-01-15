package todolist.today.today.global.error.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import todolist.today.today.global.error.dto.SimpleErrorResponse;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SimpleException extends RuntimeException implements GlobalException<SimpleErrorResponse> {

    private final SimpleErrorResponse errorResponse;

}
