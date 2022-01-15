package todolist.today.today.global.error.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import todolist.today.today.global.error.dto.InvoluteErrorResponse;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class InvoluteException extends RuntimeException implements GlobalException<InvoluteErrorResponse> {

    private final InvoluteErrorResponse errorResponse;

}
